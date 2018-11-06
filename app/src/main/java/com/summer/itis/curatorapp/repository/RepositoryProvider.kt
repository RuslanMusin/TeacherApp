package com.summer.itis.curatorapp.repository

import com.summer.itis.curatorapp.api.ApiFactory.Companion.curatorService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.skillService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.studentService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.workService
import com.summer.itis.curatorapp.api.services.CuratorService
import com.summer.itis.curatorapp.repository.common.CommonRepository
import com.summer.itis.curatorapp.repository.common.CommonRepositoryImpl
import com.summer.itis.curatorapp.repository.curator.CuratorRepository
import com.summer.itis.curatorapp.repository.curator.CuratorRepositoryImpl
import com.summer.itis.curatorapp.repository.skill.SkillRepository
import com.summer.itis.curatorapp.repository.skill.SkillRepositoryImpl
import com.summer.itis.curatorapp.repository.student.StudentRepositoryImpl
import com.summer.itis.curatorapp.repository.work.WorkRepository
import com.summer.itis.curatorapp.repository.work.WorkRepositoryImpl


class RepositoryProvider {

    companion object {

        //table_names
        const val USERS = "users"
        const val USER_FRIENDS = "user_friends"
        const val USERS_ABSTRACT_CARDS = "users_abstract_cards"
        const val USERS_TESTS = "users_tests"
        const val USERS_CARDS = "users_cards"
        const val ABSTRACT_CARDS = "abstract_cards"
        const val CARDS = "test_cards"
        const val TESTS = "tests"
        const val TEST_COMMENTS = "test_comments"
        const val CARD_COMMENTS = "card_comments"
        const val LOBBIES = "lobbies"
        const val USERS_LOBBIES = "users_lobbies"

        val curatorRepository: CuratorRepository by lazy {
            CuratorRepositoryImpl(curatorService)
        }

        val commonRepository: CommonRepository by lazy {
            CommonRepositoryImpl()
        }

        val skillRepository: SkillRepository by lazy {
            SkillRepositoryImpl(skillService)
        }

        val worksRepository: WorkRepository by lazy {
            WorkRepositoryImpl(workService)
        }

        val studentRepository: StudentRepositoryImpl by lazy {
            StudentRepositoryImpl(studentService)
        }

    }
}