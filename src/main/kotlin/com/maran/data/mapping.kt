package com.maran.data

import com.maran.data.daos.*
import com.maran.data.models.Model
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun testDaoToModel(dao: TestDao): Model.Test {
    return Model.Test(
        id = dao.id.value,
        name = dao.name,
        author = userDaoToModel(dao.author),
        testType = dao.type,
        theme = themeDaoToModel(dao.theme),
        description = dao.description
    )
}

fun answerDaoToModel(dao: AnswerDao): Model.Answer {
    return Model.Answer(
        id = dao.id.value,
        question = questionDaoToModel(dao.question),
        text = dao.text,
        isCorrect = dao.isCorrect,
        personality = dao.personality,
    )
}

fun questionDaoToModel(dao: QuestionDao): Model.Question {
    return Model.Question(id = dao.id.value, text = dao.text, test = testDaoToModel(dao.test), order = dao.order)
}

fun resultDaoToModel(dao: ResultDao): Model.Result {
    return Model.Result(id = dao.id.value, test = testDaoToModel(dao.test), resultMessage = dao.resultMessage, maxPoints = dao.maxPoints, personality = dao.personality)
}

fun roleDaoToModel(dao: RoleDao): Model.Role {
    return Model.Role(id = dao.id.value, name = dao.name)
}

fun themeDaoToModel(dao: ThemeDao): Model.Theme {
    return Model.Theme(id = dao.id.value, name = dao.name)
}
fun userDaoToModel(dao: UserDao): Model.User {
    return Model.User(id = dao.id.value, username = dao.username, password = dao.password, role = roleDaoToModel(dao.role))
}
