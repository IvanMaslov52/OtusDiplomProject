package models.services.impls

import com.google.inject.Inject
import models.CommentDTO
import models.dao.Comment
import models.repository.{CommentRepository, UserRepository}
import models.services.CommentService

class CommentServiceImpl @Inject()(commentRepository: CommentRepository, userRepository: UserRepository) extends CommentService {

  override def findCommentByTask(taskId: Int): List[CommentDTO] = commentRepository.findCommentByTask(taskId)

  override def saveComment(dto: CommentDTO): Unit = {
    for {
      user <- userRepository.findByUsername(dto.creator)
    } yield commentRepository.insert(Comment(creator = user.id, task = dto.task, description = dto.description))
  }
}
