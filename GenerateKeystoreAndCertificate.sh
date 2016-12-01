# Sample Keystore generation. Use this for quick creation of your own key
# keytool -genkeypair -alias aio-adal -keystore aio-adal-keystore.jks  -keyalg RSA  -sigalg SHA1withRSA  -keysize 1024 -ext SAN=DNS:localhost,IP:127.0.0.1 -validity 9999 -dname "CN=John Doe, OU=Business Improvement, O=Contoso LTD, L=Newcastle, ST=NSW, C=AU" -keypass my_key_password -storepass my_store_password
keytool -genkeypair -alias aio-adal -keystore aio-adal-keystore.jks  -keyalg RSA  -sigalg SHA1withRSA  -keysize 1024 -ext SAN=DNS:localhost,IP:127.0.0.1 -validity 9999
keytool -export -alias aio-adal -rfc -keystore aio-adal-keystore.jks > server.cert
