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
        if(codes.length) {
            var message = $.grep(codes[0].codes, function (e) {return e.code == code; });
            if(message.length) {
                return message[0].code;
            }
            else {
                return "Klaidos pranesimas nebuvo rastas";
            }
        }
        else {
            return "Klaidos pranesimas nebuvo rastas";
        }
    }
}]);