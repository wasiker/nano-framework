/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nanoframework.orm.jedis.sharded;

import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.nanoframework.commons.loader.LoaderException;
import org.nanoframework.commons.loader.PropertiesLoader;
import org.nanoframework.orm.jedis.RedisClientPool;

import junit.framework.TestSuite;

/**
 *
 * @author yanghe
 * @since 1.4.10
 */
@RunWith(Suite.class)
@SuiteClasses({ ShardedHashTests.class, ShardedInfoTests.class, ShardedKeyTests.class, ShardedListTests.class, ShardedSortedSetTests.class,
        ShardedSetTests.class })
public class RedisTestSuite extends TestSuite {

    @BeforeClass
    public static void before() throws LoaderException, IOException {
        final Properties prop = PropertiesLoader.load("/redis-test.properties");
        RedisClientPool.POOL.initRedisConfig(prop).createJedis();
        RedisClientPool.POOL.bindGlobal();
    }

}
