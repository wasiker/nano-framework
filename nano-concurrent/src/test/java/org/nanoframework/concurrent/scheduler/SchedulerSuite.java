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
package org.nanoframework.concurrent.scheduler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.nanoframework.concurrent.scheduler.cluster.config.ConfigureListenerTest;
import org.nanoframework.concurrent.scheduler.cluster.loader.LoaderTest;

/**
 *
 * @author yanghe
 * @since 1.4.9
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ConfigureListenerTest.class,
    LoaderTest.class,
    org.nanoframework.concurrent.scheduler.longwait.SchedulerTest.class,
    org.nanoframework.concurrent.scheduler.tests.SchedulerTest.class
})
public class SchedulerSuite {

}
