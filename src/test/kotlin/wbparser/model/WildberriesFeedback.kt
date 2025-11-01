package wbparser.model

data class WildberriesFeedback(
    val publicationDate: String,
    val author: String,
    val text: String,
    val rating: Int,
    val photoCount: Int,
    val hasVideo: Boolean,
    val tags: List<String>
)

