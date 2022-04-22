#!/bin/bash

#!/bin/bash

source $WORKSPACE/svn_legrain-secrets/fr.legrain.secrets/bdg/applications-mobiles/app-android-client/deploiement/param.sh



PROJECT_NAME="EspaceClientBdg"
ANDROID_TOOLS=/var/opt/android/tools

ANDROID_HOME=/var/opt/android/
ANDROID_SDK=/var/opt/android/

export ANDROID_HOME
export ANDROID_SDK
export JAVA_HOME=/var/opt/java/jdk1.8.0_111_x64

cd "$WORKSPACE/svn/$PROJECT_NAME"

###
#Copie du keystore dans le répertoire de compilation
cp -rap $WORKSPACE/svn_legrain-secrets/fr.legrain.secrets/bdg/applications-mobiles/app-android-client/* .
cp variants/legrain/google-services.json app/src/legrain/
cp variants/essai1/google-services.json app/src/essai1/
###

cp -rap keystore app/

echo "" > local.properties

./gradlew assembleDebug
./gradlew assembleRelease

KEYFILE=keystore/upload_key.jks
#STORE_PASSWORD=lgrxxxx
#KEY_ALIAS=upload
#KEY_PASSWORD=lgrxxxx

./gradlew assembleRelease -Pandroid.injected.signing.store.file=$KEYFILE -Pandroid.injected.signing.store.password=$STORE_PASSWORD -Pandroid.injected.signing.key.alias=$KEY_ALIAS -Pandroid.injected.signing.key.password=$KEY_PASSWORD

ls -lh app/build/outputs/apk/*/*/*.apk

#./gradlew sonarqube \
#  -Dsonar.projectKey=EspaceClient_BDG_Android \
#  -Dsonar.host.url=https://sonar.legrain.dev \
#  -Dsonar.login=28c513c4f800c8806412aa57dd15eb304e0f1010 \
#  -Dsonar.projectName="Espace Client BDG Android"