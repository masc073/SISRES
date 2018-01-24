//jQuery(function () {
//    jQuery.noConflict();
//});
onload = startApp;

var googleUser = {};

//var startApp = function() {
function startApp() {
    gapi.load('auth2', function () {
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
            client_id: '648373580314-tk04f76nc7ickcn53ma6dln763vji45f.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
            // Request scopes in addition to 'profile' and 'email'
            //scope: 'additional_scope'
        });
        attachSignin(document.getElementById('customBtn'));
    });
}
;

function attachSignin(element) {
//    console.log(element.id);
    auth2.attachClickHandler(element, {},
            function (googleUser) {
                onSignIn(googleUser);
            },
            function (error) {
//                alert(JSON.stringify(error, undefined, 2));
            });
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}

function onSignIn(googleUser) {
    
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

//    if (googleUser.getHostedDomain() != 'a.recife.ifpe.edu.br')
//    {
//        alert("Utilize seu email instituncional.");
//        signOut();
//    }

    var id_token = googleUser.getAuthResponse().id_token;
    
    alert(id_token);

    login([{name: 'param.idToken', value: id_token}]);
}

function handleComplete(xhr, status, args) {
    var nomeDoAtributo = args.logou;
    alert(nomeDoAtributo);
}