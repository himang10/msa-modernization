/*
 * Copyright 2014-2018 the original author or authors.
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
package com.skcc.mongodb.exam05.services;

import java.util.Collection;

import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import com.skcc.mongodb.exam05.entity.BlogPost;

/**
 * Just a little helper for showing {@link BlogPost}s output on the console.
 *
 * @author Christoph Strobl
 */
public class ConsoleResultPrinter {

	public static String printResult(Collection<BlogPost> blogPosts, CriteriaDefinition criteria) {

		StringBuffer sb = new StringBuffer();
		
		sb.append(String.format("XXXXXXXXXXXX -- Found %s blogPosts matching '%s' --XXXXXXXXXXXX",
				blogPosts.size(), criteria != null ? criteria.getCriteriaObject() : "")).append("\n");

		for (BlogPost blogPost : blogPosts) {
			sb.append(blogPost).append("\n");
		}

		sb.append("XXXXXXXXXXXX -- XXXXXXXXXXXX -- XXXXXXXXXXXX\r\n");
		
		return sb.toString();
	}
}
