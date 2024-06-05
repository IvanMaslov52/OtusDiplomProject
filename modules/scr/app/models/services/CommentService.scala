package models.services

import models.CommentDTO
import models.dao.Comment

trait CommentService {
  def findCommentByTask(taskId: Int): List[CommentDTO]

  def saveComment(commentDTO: CommentDTO): Unit

}
