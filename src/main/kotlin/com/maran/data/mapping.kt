package com.maran.data

import com.maran.data.daos.*
import com.maran.data.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun testDaoToModel(dao: TestDao): Test {
    return Test(id = dao.id.value, name = dao.name, author = userDaoToModel(dao.author), type = dao.type, theme = themeDaoToModel(dao.theme))
}

fun answerDaoToModel(dao: AnswerDao): Answer {
    return Answer(id = dao.id.value, question = questionDaoToModel(dao.question), text = dao.text, isCorrect = dao.isCorrect)
}

fun questionDaoToModel(dao: QuestionDao): Question {
    return Question(id = dao.id.value, text = dao.text, test = testDaoToModel(dao.test), order = dao.order)
}

fun resultDaoToModel(dao: ResultDao): Result {
    return Result(id = dao.id.value, test = testDaoToModel(dao.test), resultMessage = dao.resultMessage)
}

fun roleDaoToModel(dao: RoleDao): Role {
    return Role(id = dao.id.value, name = dao.name)
}

fun themeDaoToModel(dao: ThemeDao): Theme {
    return Theme(id = dao.id.value, name = dao.name)
}
fun userDaoToModel(dao: UserDao): User {
    return User(id = dao.id.value, username = dao.username, password = dao.password, role = roleDaoToModel(dao.role))
}
