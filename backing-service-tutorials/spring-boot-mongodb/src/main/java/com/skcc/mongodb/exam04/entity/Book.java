/*
 * Copyright 2013-2018 the original author or authors.
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
package com.skcc.mongodb.exam04.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * An entity representing an {@link Book}. Note how we don't need any MongoDB
 * mapping annotations as {@code id} is recognized as the id property by
 * default.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Data
//@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@Document
public class Book {

	private String kind;
	private String id;
	private int avalable;
	private VolumeInfo volumeInfo;
	private SaleInfo saleInfo;
	private SearchInfo searchInfo;

}
