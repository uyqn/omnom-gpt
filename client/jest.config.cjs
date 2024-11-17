module.exports = {
    transform: {
        "^.+\\.(ts|tsx|js|jsx)$": "babel-jest",
    },
    moduleNameMapper: {
        "\\.(css|less|scss|sass)$": "identity-obj-proxy", // Mock CSS imports
        // This will mock any asset with an absolute path starting with /
        "^/.*\\.(svg|png|jpg|jpeg|gif|webp|avif|ico)$":
            "<rootDir>/__mocks__/fileMock.js",
    },
    testEnvironment: "jsdom",
};
