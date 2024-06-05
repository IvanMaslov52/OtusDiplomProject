package module

import di.AppModule
import models.repository.impls.{ChatRepositoryImpl, CommentRepositoryImpl, FriendsRepositoryImpl, ProjectRepositoryImpl, ProjectUserRepositoryImpl, TaskRepositoryImpl, UserRepositoryImpl}
import models.repository.{ChatRepository, CommentRepository, FriendsRepository, ProjectRepository, ProjectUserRepository, TaskRepository, UserRepository}
import models.services.{AuthService, CommentService, ProfileService, ProjectService, TaskService, UserService}
import models.services.impls.{AuthServiceImpl, CommentServiceImpl, ProfileServiceImpl, ProjectServiceImpl, TaskServiceImpl, UserServiceImpl}

class ScrModule extends AppModule {
  override def configure(): Unit = {
    bindSingleton[UserService, UserServiceImpl]
    bindSingleton[UserRepository, UserRepositoryImpl]
    bindSingleton[ProjectRepository, ProjectRepositoryImpl]
    bindSingleton[AuthService, AuthServiceImpl]
    bindSingleton[ProjectService, ProjectServiceImpl]
    bindSingleton[ProjectUserRepository, ProjectUserRepositoryImpl]
    bindSingleton[FriendsRepository, FriendsRepositoryImpl]
    bindSingleton[ProfileService,ProfileServiceImpl]
    bindSingleton[ChatRepository,ChatRepositoryImpl]
    bindSingleton[TaskRepository,TaskRepositoryImpl]
    bindSingleton[TaskService,TaskServiceImpl]
    bindSingleton[CommentService,CommentServiceImpl]
    bindSingleton[CommentRepository,CommentRepositoryImpl]
  }
}
