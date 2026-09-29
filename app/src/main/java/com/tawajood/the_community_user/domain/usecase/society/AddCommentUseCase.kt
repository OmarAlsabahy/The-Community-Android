package com.tawajood.the_community_user.domain.usecase.society

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: ISocietyHomeRepository)
    : BaseUseCase<AddCommentUseCase.AddCommentRequest, Any?>() {
    override suspend fun execute(params: AddCommentRequest): RequestState<Any?>
    = repository.addComment(params.postId,params.comment)

    data class AddCommentRequest(val postId: Int,val comment: String)
}