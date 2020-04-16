/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skcc.mongodb.exam07.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam07.entity.Store;
import com.skcc.mongodb.exam07.repository.StoreRepository;

/**
 * Integration tests for {@link StoreRepository}.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Service
public class StoreRepositoryService {

	private static final GeoJsonPolygon GEO_JSON_POLYGON = new GeoJsonPolygon(
			new Point(-73.992514, 40.758934),
			new Point(-73.961138, 40.760348), 
			new Point(-73.991658, 40.730006), 
			new Point(-73.992514, 40.758934));

	@Autowired StoreRepository repository;
	@Autowired MongoOperations operations;

	/**
	 * Get all the Starbucks stores within the triangle defined by
	 *
	 * <pre>
	 * <ol>
	 * <li>43rd & Ninth</li><li>60th & First</li><li>Fresh Meadows</li>
	 * <ol>
	 * </pre>
	 *
	 * Using {@code $geoWithin} and {@code $geometry} operators.
	 *
	 * <pre>
	 * <code>
	 * {
	 *   "location": {
	 *     "$geoWithin": {
	 *       "geometry": {
	 *         "type": "Polygon",
	 *         "coordinates": [
	 *           [
	 *             [-73.992514,40.758934],
	 *             [-73.961138,40.760348],
	 *             [-73.991658,40.730006],
	 *             [-73.992514,40.758934]
	 *           ]
	 *         ]
	 *       }
	 *     }
	 *   }
	 * }
	 * </code>
	 *
	 * <pre>
	 */
	public List<Store> findWithinGeoJsonPolygon() {
		List<Store> findByLocationWithin = repository.findByLocationWithin(GEO_JSON_POLYGON);
		return findByLocationWithin;
	}

	/**
	 * The legacy format alternative to {@link #findWithinGeoJsonPolygon()}.
	 *
	 * <pre>
	 * <code>
	 * {
	 *   "location" : {
	 *     "$geoWithin" : {
	 *        "$polygon" : [ [ -73.992514, 40.758934 ] , [ -73.961138, 40.760348 ] , [ -73.991658, 40.730006 ] ]
	 *     }
	 *   }
	 * }
	 * </code>
	 *
	 * <pre>
	 */
	public List<Store> findWithinLegacyPolygon() {
		List<Store> findByLocationWithin = repository.findByLocationWithin(
				new Polygon(
					new Point(-73.992514, 40.758934), 
					new Point(-73.961138, 40.760348),
					new Point(-73.991658, 40.730006)));
		
		return findByLocationWithin;
	}

	/**
	 * The {@code $geoIntersects} keyword is not yet available via {@link Criteria} we need to fall back to manual
	 * creation of the query using the registered {@link MongoConverter} for {@link GeoJson} conversion.
	 */
	public List<Store> findStoresThatIntersectGivenPolygon() {

		Document geoJsonDbo = new Document();

		operations.getConverter().write(GEO_JSON_POLYGON, geoJsonDbo);

		BasicQuery bq = new BasicQuery(
				new Document("location", new Document("$geoIntersects", new Document("$geometry", geoJsonDbo))));

		return operations.find(bq, Store.class);
	}
}
