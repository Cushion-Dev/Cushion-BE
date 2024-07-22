JAR_NAME="cushion-0.0.1-SNAPSHOT.jar"
JAR_PATH="/root/cushion/build/libs/$JAR_NAME"

echo "Deploying the application..."

# 기존 실행 중인 애플리케이션 종료
PID=$(pgrep -f $JAR_NAME)
if [ ! -z "$PID" ]; then
    echo "Stopping existing application with PID $PID..."
    kill -9 $PID
else
    echo "No existing application found running."
fi

# 새로운 애플리케이션 실행
sudo nohup java -jar -Dspring.profiles.active=dev $JAR_PATH > /dev/null 2>&1 &

echo "Deployment complete."