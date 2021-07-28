/**
 * Copyright 2020 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package secure

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.config.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.days

val Application.jwtConfig: ApplicationConfig
    get() = environment.config.config("jwt")


val Application.jwtLifetime: Duration
    get() = 15.days

object JwtConfig {
    private const val secret = "HDZZh35d82zdzHJFF86tt"
    private const val issuser = "http://drill-4-j/"
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuser)
        .build()

    fun makeToken(lifetime: Duration): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuser)
        .withClaim("id", 1)
        .withClaim("role", "admin")
        .withExpiresAt(lifetime.toExpiration())
        .sign(algorithm)
}

private fun Duration.toExpiration() = Date(System.currentTimeMillis() + toLongMilliseconds())

