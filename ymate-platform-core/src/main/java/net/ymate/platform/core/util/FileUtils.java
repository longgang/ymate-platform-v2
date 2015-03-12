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
package net.ymate.platform.core.util;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 文件处理工具类
 *
 * @author 刘镇 (suninformation@163.com) on 14-8-17
 * @version 1.0
 */
public class FileUtils {

    private static final Map<String, String> __MIME_TYPE_MAPS = new HashMap<String, String>();

    static {
        Properties _configs = new Properties();
        InputStream _in = FileUtils.class.getClassLoader().getResourceAsStream("mimetypes-conf.properties");
        if (_in == null) {
            _in = FileUtils.class.getClassLoader().getResourceAsStream("META-INF/mimetypes-default-conf.properties");
        }
        if (_in != null) try {
            _configs.load(_in);
            for (Object _key : _configs.keySet()) {
                String[] _values = StringUtils.split(_configs.getProperty((String) _key, ""), "|");
                for (String _value : _values) {
                    __MIME_TYPE_MAPS.put(_value, (String) _key);
                }
            }
        } catch (Exception e) {
            // DO NOTHING...
        } finally {
            try {
                _in.close();
            } catch (IOException e) {
                // DO NOTHING...
            }
        }
    }

    /**
     * @param extName 文件扩展名
     * @return 根据文件扩展名获取对应的MIME_TYPE类型
     */
    public static String getFileMimeType(String extName) {
        if (extName.charAt(0) == '.') {
            extName = extName.substring(1);
        }
        return __MIME_TYPE_MAPS.get(extName);
    }

    /**
     * @param fileName 原始文件名称
     * @return 提取文件扩展名称，若不存在扩展名则返回原始文件名称
     */
    public static String getExtName(String fileName) {
        String suffix = "";
        int pos = fileName.lastIndexOf('.');
        if (pos > 0 && pos < fileName.length() - 1) {
            suffix = fileName.substring(pos + 1);
        }
        return suffix;
    }

    /**
     * @param url
     * @return 将URL地址转换成File对象, 若url指向的是jar包中文件，则返回null
     */
    public static File toFile(URL url) {
        if ((url == null) || (!url.getProtocol().equals("file"))) {
            return null;
        }
        String filename = url.getFile().replace('/', File.separatorChar);
        int pos = 0;
        while ((pos = filename.indexOf('%', pos)) >= 0) {
            if (pos + 2 < filename.length()) {
                String hexStr = filename.substring(pos + 1, pos + 3);
                char ch = (char) Integer.parseInt(hexStr, 16);
                filename = filename.substring(0, pos) + ch + filename.substring(pos + 3);
            }
        }
        return new File(filename);
    }

    /**
     * @param filePath
     * @return 将文件路径转换成URL对象, 返回值可能为NULL, 若想将jar包中文件，必须使用URL.toString()方法生成filePath参数—即以"jar:"开头
     */
    public static URL toURL(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new NullArgumentException("filePath");
        }
        try {
            if (!filePath.startsWith("jar:")
                    && !filePath.startsWith("file:")
                    && !filePath.startsWith("zip:")
                    && !filePath.startsWith("http:")
                    && !filePath.startsWith("ftp:")) {

                return new File(filePath).toURI().toURL();
            }
            return new URL(filePath);
        } catch (MalformedURLException e) {
            // DO NOTHING...
        }
        return null;
    }

}