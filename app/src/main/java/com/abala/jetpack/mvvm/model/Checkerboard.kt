package com.abala.jetpack.mvvm.model

class Checkerboard {
    private val palaces = Array(3) {
        Array(3) {
            Palace()
        }
    }
    var winner: Player? = null
    var chessPlayer: Player = Player.O
    var gameState: GameState? = null

    init {
        reset()
    }

    fun reset() {
        winner = null
        chessPlayer = Player.O
        gameState = GameState.PLAYING
        resetCheckerboard()
    }

    private fun resetCheckerboard() {
        for (x in 0 until 3) {
            for (y in 0 until 3) {
                palaces[x][y] = Palace()
            }
        }
    }

     fun done(x: Int, y: Int): Player? {
        var currentPlayer: Player? = null
        if (isPalaceCanDone(x, y)) {
            palaces[x][y].player = chessPlayer
            currentPlayer = chessPlayer
            if (isChessCompleted(chessPlayer, x, y)) {
                winner = chessPlayer
                gameState = GameState.COMPLETED
            } else {
                roleExchange()
            }
        }
        return currentPlayer
    }

    private fun isChessCompleted(player: Player, x: Int, y: Int): Boolean {
        return (palaces[x][0].player === player // 3-行
                && palaces[x][1].player === player
                && palaces[x][2].player === player)
                || (palaces[0][y].player === player // 3-列
                && palaces[1][y].player === player
                && palaces[2][y].player === player)
                || (x == y // 3-对角线
                && palaces[0][0].player === player
                && palaces[1][1].player === player
                && palaces[2][2].player === player)
                || (x + y == 2 // 3-反对角线
                && palaces[0][2].player === player
                && palaces[1][1].player === player
                && palaces[2][0].player === player)
    }

    private fun roleExchange() {
        chessPlayer = if (chessPlayer == Player.O) Player.X else Player.O
    }

    private fun isPalaceCanDone(x: Int, y: Int): Boolean {
        if (gameState == GameState.COMPLETED) {
            return false
        } else if (isPalaceDone(x, y)) {
            return false
        }
        return true
    }

    private fun isPalaceDone(x: Int, y: Int): Boolean {
        return palaces[x][y].player != null
    }

}