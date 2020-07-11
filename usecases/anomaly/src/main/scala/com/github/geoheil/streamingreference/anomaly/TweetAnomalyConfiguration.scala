// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.anomaly

case class OauthApiAuth(
    apiKey: String,
    apiSecret: String,
    tokenKey: String,
    tokenSecret: String
)
case class TweetAnomalyConfiguration(
    twitter: OauthApiAuth
)
