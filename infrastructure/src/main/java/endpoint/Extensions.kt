package org.agh.eaiib.endpoint

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.agh.eaiib.integration.session.SessionData


fun ApplicationCall.getUserId() = this.sessions.get<SessionData>()?.userId ?: "lol"