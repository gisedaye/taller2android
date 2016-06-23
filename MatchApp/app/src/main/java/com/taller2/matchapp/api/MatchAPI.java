package com.taller2.matchapp.api;

/**
 * MatchAPI parameters saved into the app shared preferences
 */
public class MatchAPI {

    private static final String LOG = "MatchAPI";

    private static final String LOGIN_ENDPOINT = "/api/accounts/login";
    private static final String REGISTER_ENDPOINT = "/api/accounts/signup";
    private static final String INTERESTS_ENDPOINT = "/api/accounts/interests";

    private static final String MATCHES_ENDPOINT = "/api/matches/";
    private static final String CANDIDATES_ENDPOINT = "/api/matches/candidates?latitude=%s&longitude=%s&radius=%s";

    private static final String LIKE_ENDPOINT = "/api/accounts/%s/like";
    private static final String DISLIKE_ENDPOINT = "/api/accounts/%s/dislike";

    private static final String GET_MESSAGES_ENDPOINT = "/api/matches/%s/messages";

    private static final String POST_A_MESSAGE_ENDPOINT = "/api/matches/%s/message";


    private static final String APP_SERVER_IP = "192.168.43.249";
    private static final String APP_SERVER_PORT = "8083";


    private static String getAppServerURL() {
        return "http://" + APP_SERVER_IP + ":" + APP_SERVER_PORT;
    }

    public static String getLoginEndpoint() {
        return getAppServerURL() + LOGIN_ENDPOINT;
    }

    public static String getRegisterEndpoint() {
        return getAppServerURL() + REGISTER_ENDPOINT;
    }


    public static String getInterestsEndpoint() {
        return getAppServerURL() + INTERESTS_ENDPOINT;
    }

    public static String getCandidatesEndpoint(String latitude, String longitude, String radius) {
        return getAppServerURL() + String.format(CANDIDATES_ENDPOINT, latitude, longitude, radius);
    }

    public static String getMatchesEndpoint() {
        return getAppServerURL() + MATCHES_ENDPOINT;
    }

    public static String getMessagesEndpoint(String chatID) {
        return getAppServerURL() + String.format(GET_MESSAGES_ENDPOINT, chatID);
    }

    public static String postAMessageEndpint(String chatID) {
        return getAppServerURL() + String.format(POST_A_MESSAGE_ENDPOINT, chatID);
    }

    public static String getLikeEndpoint(String username) {
        return getAppServerURL() + String.format(LIKE_ENDPOINT, username);
    }

    public static String getDislikeEndpoint(String username) {
        return getAppServerURL() + String.format(DISLIKE_ENDPOINT, username);
    }
}