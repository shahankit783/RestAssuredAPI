$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("feature/team.feature");
formatter.feature({
  "line": 1,
  "name": "Testing for Users",
  "description": "",
  "id": "testing-for-users",
  "keyword": "Feature"
});
formatter.before({
  "duration": 1147274615,
  "status": "passed"
});
formatter.before({
  "duration": 167971,
  "status": "passed"
});
formatter.before({
  "duration": 969453,
  "status": "passed"
});
formatter.scenario({
  "line": 11,
  "name": "Health check with wrong url",
  "description": "",
  "id": "testing-for-users;health-check-with-wrong-url",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 10,
      "name": "@TC-1"
    },
    {
      "line": 10,
      "name": "@regression"
    },
    {
      "line": 10,
      "name": "@reqres"
    }
  ]
});
formatter.step({
  "line": 12,
  "name": "Request headers",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 13
    },
    {
      "cells": [
        "Content-Type",
        "application/json"
      ],
      "line": 14
    },
    {
      "cells": [
        "Accept",
        "application/json"
      ],
      "line": 15
    }
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "Request body as json",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 17
    },
    {
      "cells": [
        "fileName",
        "post-create-user.json"
      ],
      "line": 18
    }
  ],
  "keyword": "And "
});
formatter.step({
  "comments": [
    {
      "line": 19,
      "value": "#|name| randomString |"
    },
    {
      "line": 20,
      "value": "#|job | automation|"
    }
  ],
  "line": 21,
  "name": "user hits \"users\" post api",
  "keyword": "When "
});
formatter.step({
  "line": 22,
  "name": "the status code is 201",
  "keyword": "Then "
});
formatter.step({
  "line": 23,
  "name": "Response should contain",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 24
    },
    {
      "cells": [
        "name",
        "abc"
      ],
      "line": 25
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 26,
  "name": "get the id from the response",
  "keyword": "And "
});
formatter.match({
  "location": "MyStepdefs.request_headers_are(DataTable)"
});
formatter.result({
  "duration": 274977656,
  "status": "passed"
});
formatter.match({
  "location": "MyStepdefs.request_body_as_json(DataTable)"
});
formatter.result({
  "duration": 34878080,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "users",
      "offset": 11
    },
    {
      "val": "post",
      "offset": 18
    }
  ],
  "location": "MyStepdefs.api_request_is_made(String,String)"
});
formatter.result({
  "duration": 1491077008,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "201",
      "offset": 19
    }
  ],
  "location": "MyStepdefs.verify_status_code(int)"
});
formatter.result({
  "duration": 73819271,
  "status": "passed"
});
formatter.match({
  "location": "MyStepdefs.response_should_contain_data(DataTable)"
});
formatter.result({
  "duration": 581139081,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "id",
      "offset": 8
    }
  ],
  "location": "MyStepdefs.get_the_value_from_response(String)"
});
formatter.result({
  "duration": 18292441,
  "status": "passed"
});
formatter.uri("feature/users.feature");
formatter.feature({
  "line": 1,
  "name": "Testing for Users",
  "description": "",
  "id": "testing-for-users",
  "keyword": "Feature"
});
formatter.before({
  "duration": 2157438,
  "status": "passed"
});
formatter.before({
  "duration": 149352,
  "status": "passed"
});
formatter.before({
  "duration": 254943,
  "status": "passed"
});
formatter.scenario({
  "line": 5,
  "name": "Health check with wrong url",
  "description": "",
  "id": "testing-for-users;health-check-with-wrong-url",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 4,
      "name": "@TC-1"
    },
    {
      "line": 4,
      "name": "@regression"
    },
    {
      "line": 4,
      "name": "@reqres"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "Request headers",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 7
    },
    {
      "cells": [
        "Content-Type",
        "application/json"
      ],
      "line": 8
    },
    {
      "cells": [
        "Accept",
        "application/json"
      ],
      "line": 9
    }
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "Request body as json",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 11
    },
    {
      "cells": [
        "fileName",
        "post-create-user.json"
      ],
      "line": 12
    },
    {
      "cells": [
        "name",
        "randomString"
      ],
      "line": 13
    },
    {
      "cells": [
        "job",
        "automation"
      ],
      "line": 14
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "user hits \"users\" post api",
  "keyword": "When "
});
formatter.step({
  "line": 16,
  "name": "the status code is 201",
  "keyword": "Then "
});
formatter.step({
  "line": 17,
  "name": "Response should contain",
  "rows": [
    {
      "cells": [
        "key",
        "value"
      ],
      "line": 18
    },
    {
      "cells": [
        "name",
        "randomString"
      ],
      "line": 19
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 20,
  "name": "get the id from the response",
  "keyword": "And "
});
formatter.match({
  "location": "MyStepdefs.request_headers_are(DataTable)"
});
formatter.result({
  "duration": 4854197,
  "status": "passed"
});
formatter.match({
  "location": "MyStepdefs.request_body_as_json(DataTable)"
});
formatter.result({
  "duration": 593520881,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "users",
      "offset": 11
    },
    {
      "val": "post",
      "offset": 18
    }
  ],
  "location": "MyStepdefs.api_request_is_made(String,String)"
});
formatter.result({
  "duration": 35306416,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "201",
      "offset": 19
    }
  ],
  "location": "MyStepdefs.verify_status_code(int)"
});
formatter.result({
  "duration": 3911529,
  "status": "passed"
});
formatter.match({
  "location": "MyStepdefs.response_should_contain_data(DataTable)"
});
formatter.result({
  "duration": 38200482,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "id",
      "offset": 8
    }
  ],
  "location": "MyStepdefs.get_the_value_from_response(String)"
});
formatter.result({
  "duration": 45101139,
  "status": "passed"
});
});