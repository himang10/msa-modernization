package com.skcc.mongodb.exam07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam07.entity.Store;
import com.skcc.mongodb.exam07.services.StoreRepositoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam7")
public class GeoJsonRestController {

	@Autowired
	MongoOperations operations;

	@Autowired
	private StoreRepositoryService storeRepositoryService;

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
	 * http://jsfiddle.net/cra6z87q/3/
	 * <pre>
	 */
	@ApiOperation(value = "삼각형 범위에 포함된 상점 찾기")
	@GetMapping("/findWithinGeoJsonPolygon")
	public ResponseEntity<List<Store>> findWithinGeoJsonPolygon() {
		return new ResponseEntity<>(storeRepositoryService.findWithinGeoJsonPolygon(), HttpStatus.OK);
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
	@ApiOperation(value = "좌표 범위에 포함된 상점 찾기")
	@GetMapping("/findWithinLegacyPolygon")
	public ResponseEntity<List<Store>> findWithinLegacyPolygon() {
		return new ResponseEntity<>(storeRepositoryService.findWithinLegacyPolygon(), HttpStatus.OK);
	}

	/**
	 * The {@code $geoIntersects} keyword is not yet available via {@link Criteria} we need to fall back to manual
	 * creation of the query using the registered {@link MongoConverter} for {@link GeoJson} conversion.
	 */
	@ApiOperation(value = "주어진 다각형과 교차하는 상점 찾기")
	@GetMapping("/findStoresThatIntersectGivenPolygon")
	public ResponseEntity<List<Store>> findStoresThatIntersectGivenPolygon() {
		return new ResponseEntity<>(storeRepositoryService.findStoresThatIntersectGivenPolygon(), HttpStatus.OK);
	}
}