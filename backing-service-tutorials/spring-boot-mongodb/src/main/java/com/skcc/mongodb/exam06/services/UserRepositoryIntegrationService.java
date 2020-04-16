/*
 * Copyright 2016-2018 the original author or authors.
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
package com.skcc.mongodb.exam06.services;

import static org.springframework.data.domain.ExampleMatcher.matching;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam06.entity.Person;
import com.skcc.mongodb.exam06.repository.QueryByExampleUserRepository;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @author Jens Schauder
 */
@SuppressWarnings("unused")
@Service
public class UserRepositoryIntegrationService {

	@Autowired QueryByExampleUserRepository repository;

	Person skyler, walter, flynn, marie, hank;

	public void setUp() {
		repository.deleteAll();

		this.skyler = repository.save(new Person("Skyler", "White", 45));
		this.walter = repository.save(new Person("Walter", "White", 50));
		this.flynn = repository.save(new Person("Walter Jr. (Flynn)", "White", 17));
		this.marie = repository.save(new Person("Marie", "Schrader", 38));
		this.hank = repository.save(new Person("Hank", "Schrader", 43));
	}

	/**
	 * @see #153
	 */
	
	public long countBySimpleExample(String lastname) {

		Example<Person> example = Example.of(new Person(null, lastname, null));

		return repository.count(example);
	}

	/**
	 * @see #153
	 */
	
	public Person ignorePropertiesAndMatchByAge(int age) {
		this.flynn = new Person("Walter Jr. (Flynn)", "White", age);
		
		Example<Person> example = Example.of(flynn, matching(). //
				withIgnorePaths("firstname", "lastname"));

		return repository.findOne(example).get();
	}

	/**
	 * @see #153
	 */
	
	public Iterator<Person> substringMatching(String substring) {

		Example<Person> example = Example.of(new Person(substring, null, null), matching(). //
				withStringMatcher(StringMatcher.ENDING));

		return repository.findAll(example).iterator();
	}

	/**
	 * @see #153
	 */
	
	public Iterator<Person> regexMatching(String regex) {

		Example<Person> example = Example.of(new Person("(Skyl|Walt)er", null, null), matching(). //
				withMatcher("firstname", matcher -> matcher.regex()));

		return repository.findAll(example).iterator();
	}

	/**
	 * @see #153
	 */
	
	public Iterator<Person> matchStartingStringsIgnoreCase(String first, String last) {

		Example<Person> example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", startsWith()). //
				withMatcher("lastname", ignoreCase()));

		return repository.findAll(example).iterator();
	}

	/**
	 * @see #153
	 */
	
	public Iterator<Person> configuringMatchersUsingLambdas() {

		Example<Person> example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", matcher -> matcher.startsWith()). //
				withMatcher("lastname", matcher -> matcher.ignoreCase()));

		return repository.findAll(example).iterator();
	}

	/**
	 * @see #153
	 */
	
	public Iterator<Person> valueTransformer() {

		Example<Person> example = Example.of(new Person(null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(Integer.valueOf(50)))));

		return repository.findAll(example).iterator();
	}
}
