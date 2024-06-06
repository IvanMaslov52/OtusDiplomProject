package models.services

import models.CommentDTO

trait CommentService {
  def findCommentByTask(taskId: Int): List[CommentDTO]

  def saveComment(commentDTO: CommentDTO): Unit

}
