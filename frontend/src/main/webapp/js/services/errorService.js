app.service('errorService', [ function() {

    var errors = [
        {
            "name": "registerController",
            "codes": [
                {
                    "code": "404",
                    "message": "API puslapis nerastas"
                },
                {
                    "code": "500",
                    "message": "???"
                }
            ]
        },
        {
            "name": "loginController",
            "codes": [
                {
                    "code": "404",
                    "message": "API puslapis nerastas"
                },
                {
                    "code": "500",
                    "message": "???"
                }
            ]
        }
    ];

    this.getErrorMsgByCode = function(code, controller) {
        var codes = $.grep(errors, function(e){ return e.name == controller; });
        var message = $.grep(codes[0].codes, function(e){ return e.code == code; });
        return message[0].code;
    }
}]);