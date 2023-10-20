package com.unfunnyguy.capsule_asn.domain.model

data class Note(
    val title: String,
    val desc: String,
    val points: List<String>
){
    companion object {
        fun dummy(): Note {
            return Note(
                title = "Understanding the Universe",
                desc = "The universe is a vast and mysterious expanse that contains everything we know, from galaxies and stars to planets and life forms. While it's an incredibly complex system, here are some key ideas to help you grasp its fundamental workings:",
                points = listOf(
                    "Cosmic Expansion: Galaxies move apart due to the Big Bang.",
                    "Gravity: Governs celestial interactions, forming stars and galaxies.",
                    "Stellar Life Cycle: Stars are born, change, and die, releasing energy.",
                    "Cosmic Scale: Vast, with billions of galaxies and stars.",
                    "Dark Matter & Energy: Mysterious components.",
                    "Search for Life: Earth is unique; scientists seek life elsewhere.",
                    "Ongoing Exploration: Scientists use tools to uncover its mysteries."
                )
            )
        }
    }
}
