/*
 * Copyright 2007-2107 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.beans;

/**
 * 对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-5 下午1:43
 * @version 1.0
 */
public interface IBeanHandler {

    public static final IBeanHandler DEFAULT_HANDLER = new IBeanHandler() {
        public void init(Object owner) throws Exception {
        }

        public Object handle(Class<?> targetClass) throws Exception {
            return targetClass.newInstance();
        }
    };

    /**
     * 初始化
     *
     * @param owner
     * @throws Exception
     */
    public void init(Object owner) throws Exception;

    /**
     * 执行Bean处理过程
     *
     * @param targetClass
     * @return 返回实例化对象，若返回null则表示丢弃当前类对象
     * @throws Exception
     */
    public Object handle(Class<?> targetClass) throws Exception;
}