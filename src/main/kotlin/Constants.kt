object Constants {
    val grids = listOf(4, 5, 6, 7, 8, 9, 10, 11, 12)

    val empty: ArrayList<ArrayList<Int>> = kotlin.run {
        val grid: ArrayList<ArrayList<Int>> = arrayListOf()
        repeat(8) {
            val oneRow = arrayListOf<Int>()
            repeat(8) {
                oneRow.add(0)
            }
            grid.add(oneRow)
        }
        grid
    }
}