package me.peace.animation.activity.scene

data class Item(val name: String?, val author: String?, val fileName: String?) {
   object Engine{
       val LARGE_BASE_URL =
           "https://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/"
       val THUMB_BASE_URL =
           "https://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/"

       var ITEMS: Array<Item> =
           arrayOf(
               Item(
                   "Flying in the Light",
                   "Romain Guy",
                   "flying_in_the_light.jpg"
               ),
               Item(
                   "Caterpillar",
                   "Romain Guy",
                   "caterpillar.jpg"
               ),
               Item(
                   "Look Me in the Eye",
                   "Romain Guy",
                   "look_me_in_the_eye.jpg"
               ),
               Item(
                   "Flamingo",
                   "Romain Guy",
                   "flamingo.jpg"
               ),
               Item(
                   "Rainbow",
                   "Romain Guy",
                   "rainbow.jpg"
               ),
               Item(
                   "Over there",
                   "Romain Guy",
                   "over_there.jpg"
               ),
               Item(
                   "Jelly Fish 2",
                   "Romain Guy",
                   "jelly_fish_2.jpg"
               ),
               Item(
                   "Lone Pine Sunset",
                   "Romain Guy",
                   "lone_pine_sunset.jpg"
               )
           )

       fun getItem(id: Int): Item? {
           for (item in ITEMS) {
               if (item.getId() == id) {
                   return item
               }
           }
           return null
       }
   }

    fun getId(): Int {
        return name.hashCode() + fileName.hashCode()
    }

    fun getPhotoUrl(): String? {
        return Engine.LARGE_BASE_URL + fileName
    }

    fun getThumbnailUrl(): String? {
        return Engine.THUMB_BASE_URL + fileName
    }
}