package com.summer.itis.curatorapp.utils

import com.google.gson.Gson

//обычный класс констант и прочего общего кода
object Const {

    const val FILTER_YEAR = 1950

    const val TAG_LOG = "TAG"

    const val TAB_NAME = "TAB_NAME"

    const val MAX_LENGTH = 80
    const val MORE_TEXT = "..."

    const val SPACE = " "

    //suggestion_theme_status
    const val WAITING_CURATOR = "WAITING_CURATOR"
    const val WAITING_STUDENT = "WAITING_STUDENT"
    const val IN_PROGRESS_CURATOR = "IN_PROGRESS_CURATOR"
    const val IN_PROGRESS_STUDENT = "IN_PROGRESS_STUDENT"
    const val CHANGED_CURATOR = "CHANGED_CURATOR"
    const val CHANGED_STUDENT = "CHANGED_STUDENT"
    const val REJECTED_CURATOR = "REJECTED_CURATOR"
    const val REJECTED_STUDENT = "REJECTED_STUDENT"
    const val ACCEPTED_CURATOR = "ACCEPTED_CURATOR"
    const val ACCEPTED_STUDENT = "ACCEPTED_STUDENT"
    const val ACCEPTED_BOTH = "ACCEPTED_BOTH"

    const val SUBJECT_KEY = "subject"
    const val SKILL_KEY = "skill"
    const val THEME_KEY = "suggestionTheme"

    const val COURSE_KEY = "course_key"

    const val WORK_KEY = "work_key"

    const val DESC_KEY = "desc"

    const val PERSON_TYPE = "PERSON_TYPE"
    const val CURATOR_TYPE = "CURATOR_TYPE"
    const val STUDENT_TYPE = "STUDENT_TYPE"

    const val SUGGESTION_TYPE = "SUGGESTION_TYPE"
    const val THEME_TYPE = "THEME_TYPE"

    const val TYPE = "TYPE"

    const val ALL_CHOOSED = "all_choosed"
    const val ONE_CHOOSED = "one_choosed"

    const val ADD_SKILL = 6
    const val SEND_THEME = 100
    const val FILTERS = 101

    const val REQUEST_CODE = "REQUEST_CODE"


    const val BOT_ID = "6n5OesjRMGN0jFAhP5jG9hxtaRg2"

    const val ONLINE_GAME = "online_game"
    const val BOT_GAME = "bot_game"

    const val ANY_QUANTIFIER = "ANY_QUANTIFIER"
    const val ALL_QUANTIFIER = "ALL_QUANTIFIER"


    //game modes/stadies
    const val MODE_CHANGE_CARDS = "change_cards"
    const val MODE_PLAY_GAME = "play_game"
    const val MODE_CARD_VIEW = "card_view"
    const val MODE_END_GAME = "end_game"


    //Gamer status
    const val ONLINE_STATUS = "online_status"
    const val OFFLINE_STATUS = "offline_status"
    const val STOP_STATUS = "stop_status"
    const val IN_GAME_STATUS = "in_game_status"
    const val NOT_ACCEPTED = "not_accepted"
    const val EDIT_STATUS = "edit_status"

    //SharedPreferences
    const val USER_DATA_PREFERENCES = "user_data"
    const val USER_USERNAME = "user_username"
    const val USER_PASSWORD = "user_password"

    const val ENTITY_NOT_EXIST = "not_exist"

    // Http request
    const val MESSAGING_KEY = "Authorization"
    const val CONTENT_TYPE = "Content-Type"
    const val APP_JSON = "application/json"

    const val PAGE_SIZE = 20
    const val ZERO_OFFSET = 0

    // Http response sorting
    const val DEFAULT_BOOK_SORT = "author"

    // Intent's constants
    const val ID_KEY = "id"
    const val NAME_KEY = "name"
    const val PHOTO_KEY = "photo"
    const val AUTHOR_KEY = "author"

    const val BOOK_KEY = "book"
    const val CROSSING_KEY = "crossing"
    const val USER_KEY = "user"
    const val POINT_KEY = "point"

    //Crossing type
    const val WATCHER_TYPE = "watcher"
    const val OWNER_TYPE = "owner"
    const val RESTRICT_OWNER_TYPE = "restrict_owner"
    const val FOLLOWER_TYPE = "follower"

    //Friend type
    const val ADD_FRIEND = "addF"
    const val REMOVE_FRIEND = "removeF"
    const val ADD_REQUEST = "addR"
    const val REMOVE_REQUEST = "removeR"

    //Friend's list types
    const val READER_LIST_TYPE = "type"

    const val READER_LIST = "readers"
    const val FRIEND_LIST = "friends"
    const val REQUEST_LIST = "requests"

    //Test list types
    const val OFFICIAL_LIST = "OFFICIAL"
    const val USER_LIST = "USER"
    const val MY_LIST = "MY"

    //Theme list types
    const val SUGGESTIONS_LIST = "SUGGESTIONS"
    const val MY_THEMES_LIST = "MY_THEMES"

    //Firebase constants
    const val SEP = "/"
    const val QUERY_END = "\uf8ff"

    const val QUERY_TYPE = "query"
    const val DEFAULT_TYPE = "default"

    //TestType
    const val TEST_ONE_TYPE = "test_one_type"
    const val TEST_MANY_TYPE = "test_many_type"

    //CardListType
    const val USER_LIST_CARDS = "user_cards"
    const val ALL_LIST_CARDS = "all_cards"
    //TestListType
    const val TEST_LIST_TYPE = "test_list"
    const val DEFAULT_ABSTRACT_TESTS ="def_abs_tests"
    const val USER_ABSTRACT_TESTS ="user_abs_tests"
    const val USER_TESTS ="user_tests"

    const val ABSTRACT_CARD_ID = "abs_card_id"
    const val USER_ID = "user_id"

    //TestRelation
    const val WIN_GAME = "win_game"
    const val WIN_AFTER_WIN = "after_win_test"
    const val LOSE_AFTER_WIN = "ore_after_test"
    const val TEST_AFTER_WIN = ""

    const val AFTER_TEST = "after_test"
    const val WIN_AFTER_TEST = "after_win_test"
    const val TEST_AFTER_TEST = "ore_after_test"
    const val LOSE_AFTER_TEST = ""

    const val LOSE_GAME = "lose_game"
    const val WIN_AFTER_LOSE = "after_win_test"
    const val LOSE_AFTER_LOSE = "ore_after_test"
    const val TEST_AFTER_LOSE = ""

    const val BEFORE_TEST = "before_test"

    //GameType
    const val OFFICIAL_TYPE = "official_type"
    const val USER_TYPE = "user_type"

    //UserType
    const val ADMIN_ROLE = "admin_role"
    const val USER_ROLE = "user_role"

    //image path
    const val IMAGE_START_PATH = "images/users/"
    const val AVATAR = "avatar"
    const val STUB_PATH = "https://upload.wikimedia.org/wikipedia/commons/b/ba/Leonardo_self.jpg"

    @JvmField
    val gsonConverter = Gson()

    const val COMA = ","

    //API
    //query
    const val FORMAT = "xml"
    const val ACTION_QUERY = "query"
    const val PROP = "extracts|pageimages|desc"
    const val EXINTRO = "1"
    const val EXPLAINTEXT = "1"
    const val PIPROP = "original"
    const val PILICENSE = "any"
    const val TITLES = "Толстой, Лев Николаевич"
    //opensearch
    const val ACTION_SEARCH = "opensearch"
    const val UTF_8 = "1"
    const val NAMESPACE = "0"
    const val SEARCH = "Лев Толстой"

    //Others
    const val MAX_UPLOAD_RETRY_MILLIS = 60000 //1 minute

    object Profile {
        val MAX_AVATAR_SIZE = 1280 //px, side of square
        val MIN_AVATAR_SIZE = 100 //px, side of square
        val MAX_NAME_LENGTH = 120
    }

    //Edit
    const val EDIT_SUGGESTION = 2
    const val EDIT_THEME = 3
    const val ADD_THEME_TYPE = "ADD_THEME"


}
