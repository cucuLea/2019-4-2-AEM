"use strict";

use(function () {

    var CONST = {
        PROP_TITLE: "words",
        PROP_TAG_TYPE: "type"
    }

    var title = {};

    // The actual title content
    title.text = properties.get(CONST.PROP_TITLE)|| "";

    return title;

});
