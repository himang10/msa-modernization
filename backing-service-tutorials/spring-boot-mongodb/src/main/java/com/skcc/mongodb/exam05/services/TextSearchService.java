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

import static com.skcc.mongodb.exam05.services.ConsoleResultPrinter.printResult;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam05.entity.BlogPost;
import com.skcc.mongodb.exam05.repository.BlogPostRepository;

/**
 * Integration tests showing the text search functionality using repositories.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Service
public class TextSearchService {

	@Autowired BlogPostRepository repo;

	/**
	 * Show how to do simple matching. <br />
	 * Note that text search is case insensitive and will also find entries like {@literal releases}.
	 */
	public String findAllBlogPostsWithRelease(String[] matchingAny) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(matchingAny);
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		return printResult(blogPosts, criteria);
	}

	/**
	 * Simple matching using negation.
	 */
	public String findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff(String[] matchingAny, String notMatching) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(matchingAny).notMatching(notMatching);
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		return printResult(blogPosts, criteria);
	}

	/**
	 * Phrase matching looks for the whole phrase as one.
	 */
	public String findAllBlogPostsByPhrase(String matchingPhrase) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(matchingPhrase);
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		return printResult(blogPosts, criteria);
	}

	/**
	 * Sort by relevance relying on the value marked with {@link TextScore}.
	 */
	public String findAllBlogPostsByPhraseSortByScore(String matchingPhrase) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(matchingPhrase);
		List<BlogPost> blogPosts = repo.findAllByOrderByScoreDesc(criteria);

		return printResult(blogPosts, criteria);
	}
	
	@Autowired MongoOperations operations;
	
	public String findAllBlogPostsWithRelease2(String... matchingAny) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(matchingAny);
		List<BlogPost> blogPosts = operations.find(query(criteria), BlogPost.class);

		return printResult(blogPosts, criteria);
	}

	/**
	 * Sort by relevance relying on the value marked with {@link org.springframework.data.mongodb.core.mapping.TextScore}.
	 */
	public String findAllBlogPostsByPhraseSortByScore2(String matchingPhrase) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(matchingPhrase);

		TextQuery query = new TextQuery(criteria);
		query.setScoreFieldName("score");
		query.sortByScore();

		List<BlogPost> blogPosts = operations.find(query, BlogPost.class);

		return printResult(blogPosts, criteria);
	}
}
