VERSION := $(shell cat version.properties | grep version | cut -c 9-)
SCALA_VERSION := "2.12"

version:
	echo ${VERSION}

# NOTE: you need to have a running flink cluster up first
# i.e. on a mac with homebrew
# cd /usr/local/Cellar/apache-flink/1.10.1/libexec
# ./bin/start-cluster.sh
run-local-Socket:
	./gradlew :usecases:streamingWordcount:shadowJar
	flink run --class com.github.geoheil.streamingreference.streamingwc.StreamingWordCount \
		"usecases/streamingWordcount/build/libs/streamingWordcount_${SCALA_VERSION}-${VERSION}-all.jar"  \
		--host localhost --port 9000

run-local-Tweets:
	./gradlew :usecases:tweets:shadowJar
	flink run --class com.github.geoheil.streamingreference.tweets.TweetsAnalysis \
		"usecases/tweets/build/libs/tweets_${SCALA_VERSION}-${VERSION}-all.jar" \
		-yD env.java.opts="-Dconfig.file='config/jobs/twitter-analysis.conf'"
