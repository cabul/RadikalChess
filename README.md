RadikalChess
============

Radikal Chess is a chess implementation with slightly modified rules.
It is a course project for the current semester (2014) of Fundamentos de Sistemas Inteligentes (Fundamentals of Intelligent Systems) at the University of Las Palmas (www.ulpgc.es)


Changes
-------

The following rules have been added:
- Pawns will always move just one field at a time
- There is no castling
- No-capture moves have to threaten the king or at least approach it (as of chebyshev distance)
- The game ends when there is any player with no moves left or his king checkmate

Furthermore the classical chess board has been modified:

<table>
  <tr>
  <td><strong>6</strong></td>
  <td>&#9818;</td>
  <td>&#9819;</td>
  <td>&#9820;</td>
  <td>&#9821;</td>
  </tr>
  <tr>
  <td><strong>5</strong></td>
  <td>&#9823;</td>
  <td>&#9823;</td>
  <td>&#9823;</td>
  <td>&#9823;</td>
  </tr>
  <tr>
  <td><strong>4</strong></td>
  <td>  </td>
  <td>  </td>
  <td>  </td>
  <td>  </td>
  </tr>
  <tr>
  <td><strong>3</strong></td>
  <td>  </td>
  <td>  </td>
  <td>  </td>
  <td>  </td>
  </tr>
  <tr>
  <td><strong>2</strong></td>
  <td>&#9817;</td>
  <td>&#9817;</td>
  <td>&#9817;</td>
  <td>&#9817;</td>
  </tr>
  <tr>
  <td><strong>1</strong></td>
  <td>&#9814;</td>
  <td>&#9815;</td>
  <td>&#9813;</td>
  <td>&#9812;</td>
  </tr>
  <tr>
  <td>  </td>
  <td><strong>A</strong></td>
  <td><strong>B</strong></td>
  <td><strong>C</strong></td>
  <td><strong>D</strong></td>
  </tr
</table>
