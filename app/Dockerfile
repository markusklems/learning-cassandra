FROM anapsix/alpine-java:8_jdk_unlimited

# Create app directory
RUN mkdir -p myapp
WORKDIR /myapp

# Install Maven
ENV MAVEN_VERSION 3.3.9
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn 

# Copy source code
COPY pom.xml /myapp/pom.xml
COPY src /myapp/src
# Copy bin files to more easily execute examples
COPY bin /myapp/bin
RUN chmod -R +x /myapp/bin
ENV PATH /myapp/bin:$PATH
# Compile, install dependencies, and create assembly distribution
RUN mvn install