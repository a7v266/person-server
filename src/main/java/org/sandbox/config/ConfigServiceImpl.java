package org.sandbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sandbox.domain.User;
import org.sandbox.domain.UserGroup;
import org.sandbox.service.UserGroupService;
import org.sandbox.service.UserService;
import org.sandbox.utils.FileUtils;
import org.sandbox.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;


@Service
public class ConfigServiceImpl implements ConfigService {

    @Value("${config.path}")
    private String configPath;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String CONFIG_PATH_FORMAT = "%s/%s";
    private static final String RESOURCE_PATH_FORMAT = "classpath:config/%s";

    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String FILE_USER = "user.json";
    private static final String FILE_USER_GROUP = "user-group.json";
    private static final String SECURITY_ALGORITHM = "DSA";
    private static final String SECURITY_PROVIDER = "SUN";

    @PostConstruct
    public void init() throws IOException {
        loadUserGroup();
        loadUser();
    }

    private void loadUserGroup() throws IOException {
        UserGroup[] userGroups = load(UserGroup[].class, getInputStream(FILE_USER_GROUP));
        for (UserGroup userGroup : userGroups) {
            userGroupService.mergeUserGroup(userGroup);
        }
    }

    private void loadUser() throws IOException {
        User[] users = load(User[].class, getInputStream(FILE_USER));
        for (User user : users) {
            userService.mergeUser(user);
        }
    }

    private <T> T load(Class<T> clazz, InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return objectMapper.readValue(reader, clazz);
        }
    }

    private InputStream getInputStream(String fileName) throws IOException {
        String filePath = StringUtils.format(CONFIG_PATH_FORMAT, configPath, fileName);
        if (FileUtils.isFileExist(filePath)) {
            Loggers.CONFIG_LOGGER.info("Load config file {}", filePath);
            return new FileInputStream(filePath);
        }
        String resourcePath = StringUtils.format(RESOURCE_PATH_FORMAT, fileName);
        Loggers.CONFIG_LOGGER.info("Load default resource {}", resourcePath);
        return resourceLoader.getResource(resourcePath).getInputStream();
    }
}