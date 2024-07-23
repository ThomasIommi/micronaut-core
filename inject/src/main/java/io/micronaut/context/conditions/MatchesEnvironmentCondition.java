/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.context.conditions;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.UsedByGeneratedCode;
import io.micronaut.core.util.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * The env condition.
 *
 * @param env The env
 * @author Denis Stepanov
 * @since 4.6
 */
@UsedByGeneratedCode
@Internal
public record MatchesEnvironmentCondition(String[] env) implements Condition {
    @Override
    public boolean matches(ConditionContext context) {
        BeanContext beanContext = context.getBeanContext();
        if (beanContext instanceof ApplicationContext applicationContext) {
            Environment environment = applicationContext.getEnvironment();
            Set<String> activeNames = environment.getActiveNames();
            boolean result = matches(activeNames);
            if (!result) {
                context.fail("None of the required environments [" + ArrayUtils.toString(env) + "] are active: " + activeNames);
            }
            return result;
        }
        return true;
    }

    private boolean matches(Set<String> activeNames) {
        for (String s : env) {
            if (activeNames.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatchesEnvironmentCondition that = (MatchesEnvironmentCondition) o;
        return Objects.deepEquals(env, that.env);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(env);
    }

    @Override
    public String toString() {
        return "MatchesEnvironmentCondition{" +
            "env=" + Arrays.toString(env) +
            '}';
    }
}