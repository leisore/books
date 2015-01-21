/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.leisore.coder.common;

import java.lang.reflect.Field;

/**
 * Reflection tools.
 * 
 * @author leisore
 * @since 1.0.0
 */
public class Reflections {

    /**
     * Get field value recursively. <br>
     * 
     * <pre>
     * class C {
     *     int id;
     * }
     * class B {
     *     C c;
     * }
     * class A {
     *     B b;
     * }
     * 
     * A a = new A();
     * a.b = new B();
     * a.b.c = new C();
     * a.b.c.id = 10;
     * 
     * getFieldVal(a, "b", "c", "id") will return 10.
     * </pre>
     * 
     * @param obj
     * @param fieldsNames
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getFieldVal(Object obj, String... fieldsNames) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        if (fieldsNames.length > 0) {
            String fieldName = fieldsNames[0];
            String[] leftFieldsNames = new String[fieldsNames.length - 1];
            if (leftFieldsNames.length > 0) {
                System.arraycopy(fieldsNames, 1, leftFieldsNames, 0, leftFieldsNames.length);
            }
            Class<?> clz = obj.getClass();
            while (clz != null) {
                try {
                    Field field = clz.getDeclaredField(fieldName);
                    boolean access = field.isAccessible();
                    try {
                        field.setAccessible(true);
                        Object ret = field.get(obj);
                        if (leftFieldsNames.length == 0) {
                            return ret;
                        } else {
                            return getFieldVal(ret, leftFieldsNames);
                        }
                    } finally {
                        field.setAccessible(access);
                    }
                } catch (NoSuchFieldException e) {
                    clz = clz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(obj.getClass().getName() + ":" + fieldName);
        }
        throw new IllegalArgumentException("Must given one field name at least!");
    }
}
