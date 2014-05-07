// Move masks, ordered by Square

// KING_MASK Square, Step
public static final int[][] KING_MASK = new int[24][];
static
{
KING_MASK[0] = new int[3];
KING_MASK[0][0] = 16;
KING_MASK[0][1] = 2;
KING_MASK[0][2] = 32;
KING_MASK[1] = new int[5];
KING_MASK[1][0] = 32;
KING_MASK[1][1] = 1;
KING_MASK[1][2] = 4;
KING_MASK[1][3] = 64;
KING_MASK[1][4] = 16;
KING_MASK[2] = new int[5];
KING_MASK[2][0] = 64;
KING_MASK[2][1] = 2;
KING_MASK[2][2] = 8;
KING_MASK[2][3] = 128;
KING_MASK[2][4] = 32;
KING_MASK[3] = new int[3];
KING_MASK[3][0] = 128;
KING_MASK[3][1] = 4;
KING_MASK[3][2] = 64;
KING_MASK[4] = new int[5];
KING_MASK[4][0] = 1;
KING_MASK[4][1] = 256;
KING_MASK[4][2] = 32;
KING_MASK[4][3] = 2;
KING_MASK[4][4] = 512;
KING_MASK[5] = new int[8];
KING_MASK[5][0] = 2;
KING_MASK[5][1] = 512;
KING_MASK[5][2] = 16;
KING_MASK[5][3] = 64;
KING_MASK[5][4] = 4;
KING_MASK[5][5] = 1;
KING_MASK[5][6] = 1024;
KING_MASK[5][7] = 256;
KING_MASK[6] = new int[8];
KING_MASK[6][0] = 4;
KING_MASK[6][1] = 1024;
KING_MASK[6][2] = 32;
KING_MASK[6][3] = 128;
KING_MASK[6][4] = 8;
KING_MASK[6][5] = 2;
KING_MASK[6][6] = 2048;
KING_MASK[6][7] = 512;
KING_MASK[7] = new int[5];
KING_MASK[7][0] = 8;
KING_MASK[7][1] = 2048;
KING_MASK[7][2] = 64;
KING_MASK[7][3] = 4;
KING_MASK[7][4] = 1024;
KING_MASK[8] = new int[5];
KING_MASK[8][0] = 16;
KING_MASK[8][1] = 4096;
KING_MASK[8][2] = 512;
KING_MASK[8][3] = 32;
KING_MASK[8][4] = 8192;
KING_MASK[9] = new int[8];
KING_MASK[9][0] = 32;
KING_MASK[9][1] = 8192;
KING_MASK[9][2] = 256;
KING_MASK[9][3] = 1024;
KING_MASK[9][4] = 64;
KING_MASK[9][5] = 16;
KING_MASK[9][6] = 16384;
KING_MASK[9][7] = 4096;
KING_MASK[10] = new int[8];
KING_MASK[10][0] = 64;
KING_MASK[10][1] = 16384;
KING_MASK[10][2] = 512;
KING_MASK[10][3] = 2048;
KING_MASK[10][4] = 128;
KING_MASK[10][5] = 32;
KING_MASK[10][6] = 32768;
KING_MASK[10][7] = 8192;
KING_MASK[11] = new int[5];
KING_MASK[11][0] = 128;
KING_MASK[11][1] = 32768;
KING_MASK[11][2] = 1024;
KING_MASK[11][3] = 64;
KING_MASK[11][4] = 16384;
KING_MASK[12] = new int[5];
KING_MASK[12][0] = 256;
KING_MASK[12][1] = 65536;
KING_MASK[12][2] = 8192;
KING_MASK[12][3] = 512;
KING_MASK[12][4] = 131072;
KING_MASK[13] = new int[8];
KING_MASK[13][0] = 512;
KING_MASK[13][1] = 131072;
KING_MASK[13][2] = 4096;
KING_MASK[13][3] = 16384;
KING_MASK[13][4] = 1024;
KING_MASK[13][5] = 256;
KING_MASK[13][6] = 262144;
KING_MASK[13][7] = 65536;
KING_MASK[14] = new int[8];
KING_MASK[14][0] = 1024;
KING_MASK[14][1] = 262144;
KING_MASK[14][2] = 8192;
KING_MASK[14][3] = 32768;
KING_MASK[14][4] = 2048;
KING_MASK[14][5] = 512;
KING_MASK[14][6] = 524288;
KING_MASK[14][7] = 131072;
KING_MASK[15] = new int[5];
KING_MASK[15][0] = 2048;
KING_MASK[15][1] = 524288;
KING_MASK[15][2] = 16384;
KING_MASK[15][3] = 1024;
KING_MASK[15][4] = 262144;
KING_MASK[16] = new int[5];
KING_MASK[16][0] = 4096;
KING_MASK[16][1] = 1048576;
KING_MASK[16][2] = 131072;
KING_MASK[16][3] = 8192;
KING_MASK[16][4] = 2097152;
KING_MASK[17] = new int[8];
KING_MASK[17][0] = 8192;
KING_MASK[17][1] = 2097152;
KING_MASK[17][2] = 65536;
KING_MASK[17][3] = 262144;
KING_MASK[17][4] = 16384;
KING_MASK[17][5] = 4096;
KING_MASK[17][6] = 4194304;
KING_MASK[17][7] = 1048576;
KING_MASK[18] = new int[8];
KING_MASK[18][0] = 16384;
KING_MASK[18][1] = 4194304;
KING_MASK[18][2] = 131072;
KING_MASK[18][3] = 524288;
KING_MASK[18][4] = 32768;
KING_MASK[18][5] = 8192;
KING_MASK[18][6] = 8388608;
KING_MASK[18][7] = 2097152;
KING_MASK[19] = new int[5];
KING_MASK[19][0] = 32768;
KING_MASK[19][1] = 8388608;
KING_MASK[19][2] = 262144;
KING_MASK[19][3] = 16384;
KING_MASK[19][4] = 4194304;
KING_MASK[20] = new int[3];
KING_MASK[20][0] = 65536;
KING_MASK[20][1] = 2097152;
KING_MASK[20][2] = 131072;
KING_MASK[21] = new int[5];
KING_MASK[21][0] = 131072;
KING_MASK[21][1] = 1048576;
KING_MASK[21][2] = 4194304;
KING_MASK[21][3] = 262144;
KING_MASK[21][4] = 65536;
KING_MASK[22] = new int[5];
KING_MASK[22][0] = 262144;
KING_MASK[22][1] = 2097152;
KING_MASK[22][2] = 8388608;
KING_MASK[22][3] = 524288;
KING_MASK[22][4] = 131072;
KING_MASK[23] = new int[3];
KING_MASK[23][0] = 524288;
KING_MASK[23][1] = 4194304;
KING_MASK[23][2] = 262144;
}
// ROOK_MASK Square, Ray, Step
public static final int[][][] ROOK_MASK = new int[24][][];
static
{
ROOK_MASK[0] = new int[2][];
ROOK_MASK[0][0] = new int[5];
ROOK_MASK[0][0][0] = 16;
ROOK_MASK[0][0][1] = 256;
ROOK_MASK[0][0][2] = 4096;
ROOK_MASK[0][0][3] = 65536;
ROOK_MASK[0][0][4] = 1048576;
ROOK_MASK[0][1] = new int[3];
ROOK_MASK[0][1][0] = 2;
ROOK_MASK[0][1][1] = 4;
ROOK_MASK[0][1][2] = 8;
ROOK_MASK[1] = new int[3][];
ROOK_MASK[1][0] = new int[5];
ROOK_MASK[1][0][0] = 32;
ROOK_MASK[1][0][1] = 512;
ROOK_MASK[1][0][2] = 8192;
ROOK_MASK[1][0][3] = 131072;
ROOK_MASK[1][0][4] = 2097152;
ROOK_MASK[1][1] = new int[2];
ROOK_MASK[1][1][0] = 4;
ROOK_MASK[1][1][1] = 8;
ROOK_MASK[1][2] = new int[1];
ROOK_MASK[1][2][0] = 1;
ROOK_MASK[2] = new int[3][];
ROOK_MASK[2][0] = new int[5];
ROOK_MASK[2][0][0] = 64;
ROOK_MASK[2][0][1] = 1024;
ROOK_MASK[2][0][2] = 16384;
ROOK_MASK[2][0][3] = 262144;
ROOK_MASK[2][0][4] = 4194304;
ROOK_MASK[2][1] = new int[1];
ROOK_MASK[2][1][0] = 8;
ROOK_MASK[2][2] = new int[2];
ROOK_MASK[2][2][0] = 2;
ROOK_MASK[2][2][1] = 1;
ROOK_MASK[3] = new int[2][];
ROOK_MASK[3][0] = new int[5];
ROOK_MASK[3][0][0] = 128;
ROOK_MASK[3][0][1] = 2048;
ROOK_MASK[3][0][2] = 32768;
ROOK_MASK[3][0][3] = 524288;
ROOK_MASK[3][0][4] = 8388608;
ROOK_MASK[3][1] = new int[3];
ROOK_MASK[3][1][0] = 4;
ROOK_MASK[3][1][1] = 2;
ROOK_MASK[3][1][2] = 1;
ROOK_MASK[4] = new int[3][];
ROOK_MASK[4][0] = new int[4];
ROOK_MASK[4][0][0] = 256;
ROOK_MASK[4][0][1] = 4096;
ROOK_MASK[4][0][2] = 65536;
ROOK_MASK[4][0][3] = 1048576;
ROOK_MASK[4][1] = new int[1];
ROOK_MASK[4][1][0] = 1;
ROOK_MASK[4][2] = new int[3];
ROOK_MASK[4][2][0] = 32;
ROOK_MASK[4][2][1] = 64;
ROOK_MASK[4][2][2] = 128;
ROOK_MASK[5] = new int[4][];
ROOK_MASK[5][0] = new int[4];
ROOK_MASK[5][0][0] = 512;
ROOK_MASK[5][0][1] = 8192;
ROOK_MASK[5][0][2] = 131072;
ROOK_MASK[5][0][3] = 2097152;
ROOK_MASK[5][1] = new int[1];
ROOK_MASK[5][1][0] = 2;
ROOK_MASK[5][2] = new int[2];
ROOK_MASK[5][2][0] = 64;
ROOK_MASK[5][2][1] = 128;
ROOK_MASK[5][3] = new int[1];
ROOK_MASK[5][3][0] = 16;
ROOK_MASK[6] = new int[4][];
ROOK_MASK[6][0] = new int[4];
ROOK_MASK[6][0][0] = 1024;
ROOK_MASK[6][0][1] = 16384;
ROOK_MASK[6][0][2] = 262144;
ROOK_MASK[6][0][3] = 4194304;
ROOK_MASK[6][1] = new int[1];
ROOK_MASK[6][1][0] = 4;
ROOK_MASK[6][2] = new int[1];
ROOK_MASK[6][2][0] = 128;
ROOK_MASK[6][3] = new int[2];
ROOK_MASK[6][3][0] = 32;
ROOK_MASK[6][3][1] = 16;
ROOK_MASK[7] = new int[3][];
ROOK_MASK[7][0] = new int[4];
ROOK_MASK[7][0][0] = 2048;
ROOK_MASK[7][0][1] = 32768;
ROOK_MASK[7][0][2] = 524288;
ROOK_MASK[7][0][3] = 8388608;
ROOK_MASK[7][1] = new int[1];
ROOK_MASK[7][1][0] = 8;
ROOK_MASK[7][2] = new int[3];
ROOK_MASK[7][2][0] = 64;
ROOK_MASK[7][2][1] = 32;
ROOK_MASK[7][2][2] = 16;
ROOK_MASK[8] = new int[3][];
ROOK_MASK[8][0] = new int[3];
ROOK_MASK[8][0][0] = 4096;
ROOK_MASK[8][0][1] = 65536;
ROOK_MASK[8][0][2] = 1048576;
ROOK_MASK[8][1] = new int[2];
ROOK_MASK[8][1][0] = 16;
ROOK_MASK[8][1][1] = 1;
ROOK_MASK[8][2] = new int[3];
ROOK_MASK[8][2][0] = 512;
ROOK_MASK[8][2][1] = 1024;
ROOK_MASK[8][2][2] = 2048;
ROOK_MASK[9] = new int[4][];
ROOK_MASK[9][0] = new int[3];
ROOK_MASK[9][0][0] = 8192;
ROOK_MASK[9][0][1] = 131072;
ROOK_MASK[9][0][2] = 2097152;
ROOK_MASK[9][1] = new int[2];
ROOK_MASK[9][1][0] = 32;
ROOK_MASK[9][1][1] = 2;
ROOK_MASK[9][2] = new int[2];
ROOK_MASK[9][2][0] = 1024;
ROOK_MASK[9][2][1] = 2048;
ROOK_MASK[9][3] = new int[1];
ROOK_MASK[9][3][0] = 256;
ROOK_MASK[10] = new int[4][];
ROOK_MASK[10][0] = new int[3];
ROOK_MASK[10][0][0] = 16384;
ROOK_MASK[10][0][1] = 262144;
ROOK_MASK[10][0][2] = 4194304;
ROOK_MASK[10][1] = new int[2];
ROOK_MASK[10][1][0] = 64;
ROOK_MASK[10][1][1] = 4;
ROOK_MASK[10][2] = new int[1];
ROOK_MASK[10][2][0] = 2048;
ROOK_MASK[10][3] = new int[2];
ROOK_MASK[10][3][0] = 512;
ROOK_MASK[10][3][1] = 256;
ROOK_MASK[11] = new int[3][];
ROOK_MASK[11][0] = new int[3];
ROOK_MASK[11][0][0] = 32768;
ROOK_MASK[11][0][1] = 524288;
ROOK_MASK[11][0][2] = 8388608;
ROOK_MASK[11][1] = new int[2];
ROOK_MASK[11][1][0] = 128;
ROOK_MASK[11][1][1] = 8;
ROOK_MASK[11][2] = new int[3];
ROOK_MASK[11][2][0] = 1024;
ROOK_MASK[11][2][1] = 512;
ROOK_MASK[11][2][2] = 256;
ROOK_MASK[12] = new int[3][];
ROOK_MASK[12][0] = new int[2];
ROOK_MASK[12][0][0] = 65536;
ROOK_MASK[12][0][1] = 1048576;
ROOK_MASK[12][1] = new int[3];
ROOK_MASK[12][1][0] = 256;
ROOK_MASK[12][1][1] = 16;
ROOK_MASK[12][1][2] = 1;
ROOK_MASK[12][2] = new int[3];
ROOK_MASK[12][2][0] = 8192;
ROOK_MASK[12][2][1] = 16384;
ROOK_MASK[12][2][2] = 32768;
ROOK_MASK[13] = new int[4][];
ROOK_MASK[13][0] = new int[2];
ROOK_MASK[13][0][0] = 131072;
ROOK_MASK[13][0][1] = 2097152;
ROOK_MASK[13][1] = new int[3];
ROOK_MASK[13][1][0] = 512;
ROOK_MASK[13][1][1] = 32;
ROOK_MASK[13][1][2] = 2;
ROOK_MASK[13][2] = new int[2];
ROOK_MASK[13][2][0] = 16384;
ROOK_MASK[13][2][1] = 32768;
ROOK_MASK[13][3] = new int[1];
ROOK_MASK[13][3][0] = 4096;
ROOK_MASK[14] = new int[4][];
ROOK_MASK[14][0] = new int[2];
ROOK_MASK[14][0][0] = 262144;
ROOK_MASK[14][0][1] = 4194304;
ROOK_MASK[14][1] = new int[3];
ROOK_MASK[14][1][0] = 1024;
ROOK_MASK[14][1][1] = 64;
ROOK_MASK[14][1][2] = 4;
ROOK_MASK[14][2] = new int[1];
ROOK_MASK[14][2][0] = 32768;
ROOK_MASK[14][3] = new int[2];
ROOK_MASK[14][3][0] = 8192;
ROOK_MASK[14][3][1] = 4096;
ROOK_MASK[15] = new int[3][];
ROOK_MASK[15][0] = new int[2];
ROOK_MASK[15][0][0] = 524288;
ROOK_MASK[15][0][1] = 8388608;
ROOK_MASK[15][1] = new int[3];
ROOK_MASK[15][1][0] = 2048;
ROOK_MASK[15][1][1] = 128;
ROOK_MASK[15][1][2] = 8;
ROOK_MASK[15][2] = new int[3];
ROOK_MASK[15][2][0] = 16384;
ROOK_MASK[15][2][1] = 8192;
ROOK_MASK[15][2][2] = 4096;
ROOK_MASK[16] = new int[3][];
ROOK_MASK[16][0] = new int[1];
ROOK_MASK[16][0][0] = 1048576;
ROOK_MASK[16][1] = new int[4];
ROOK_MASK[16][1][0] = 4096;
ROOK_MASK[16][1][1] = 256;
ROOK_MASK[16][1][2] = 16;
ROOK_MASK[16][1][3] = 1;
ROOK_MASK[16][2] = new int[3];
ROOK_MASK[16][2][0] = 131072;
ROOK_MASK[16][2][1] = 262144;
ROOK_MASK[16][2][2] = 524288;
ROOK_MASK[17] = new int[4][];
ROOK_MASK[17][0] = new int[1];
ROOK_MASK[17][0][0] = 2097152;
ROOK_MASK[17][1] = new int[4];
ROOK_MASK[17][1][0] = 8192;
ROOK_MASK[17][1][1] = 512;
ROOK_MASK[17][1][2] = 32;
ROOK_MASK[17][1][3] = 2;
ROOK_MASK[17][2] = new int[2];
ROOK_MASK[17][2][0] = 262144;
ROOK_MASK[17][2][1] = 524288;
ROOK_MASK[17][3] = new int[1];
ROOK_MASK[17][3][0] = 65536;
ROOK_MASK[18] = new int[4][];
ROOK_MASK[18][0] = new int[1];
ROOK_MASK[18][0][0] = 4194304;
ROOK_MASK[18][1] = new int[4];
ROOK_MASK[18][1][0] = 16384;
ROOK_MASK[18][1][1] = 1024;
ROOK_MASK[18][1][2] = 64;
ROOK_MASK[18][1][3] = 4;
ROOK_MASK[18][2] = new int[1];
ROOK_MASK[18][2][0] = 524288;
ROOK_MASK[18][3] = new int[2];
ROOK_MASK[18][3][0] = 131072;
ROOK_MASK[18][3][1] = 65536;
ROOK_MASK[19] = new int[3][];
ROOK_MASK[19][0] = new int[1];
ROOK_MASK[19][0][0] = 8388608;
ROOK_MASK[19][1] = new int[4];
ROOK_MASK[19][1][0] = 32768;
ROOK_MASK[19][1][1] = 2048;
ROOK_MASK[19][1][2] = 128;
ROOK_MASK[19][1][3] = 8;
ROOK_MASK[19][2] = new int[3];
ROOK_MASK[19][2][0] = 262144;
ROOK_MASK[19][2][1] = 131072;
ROOK_MASK[19][2][2] = 65536;
ROOK_MASK[20] = new int[2][];
ROOK_MASK[20][0] = new int[5];
ROOK_MASK[20][0][0] = 65536;
ROOK_MASK[20][0][1] = 4096;
ROOK_MASK[20][0][2] = 256;
ROOK_MASK[20][0][3] = 16;
ROOK_MASK[20][0][4] = 1;
ROOK_MASK[20][1] = new int[3];
ROOK_MASK[20][1][0] = 2097152;
ROOK_MASK[20][1][1] = 4194304;
ROOK_MASK[20][1][2] = 8388608;
ROOK_MASK[21] = new int[3][];
ROOK_MASK[21][0] = new int[5];
ROOK_MASK[21][0][0] = 131072;
ROOK_MASK[21][0][1] = 8192;
ROOK_MASK[21][0][2] = 512;
ROOK_MASK[21][0][3] = 32;
ROOK_MASK[21][0][4] = 2;
ROOK_MASK[21][1] = new int[2];
ROOK_MASK[21][1][0] = 4194304;
ROOK_MASK[21][1][1] = 8388608;
ROOK_MASK[21][2] = new int[1];
ROOK_MASK[21][2][0] = 1048576;
ROOK_MASK[22] = new int[3][];
ROOK_MASK[22][0] = new int[5];
ROOK_MASK[22][0][0] = 262144;
ROOK_MASK[22][0][1] = 16384;
ROOK_MASK[22][0][2] = 1024;
ROOK_MASK[22][0][3] = 64;
ROOK_MASK[22][0][4] = 4;
ROOK_MASK[22][1] = new int[1];
ROOK_MASK[22][1][0] = 8388608;
ROOK_MASK[22][2] = new int[2];
ROOK_MASK[22][2][0] = 2097152;
ROOK_MASK[22][2][1] = 1048576;
ROOK_MASK[23] = new int[2][];
ROOK_MASK[23][0] = new int[5];
ROOK_MASK[23][0][0] = 524288;
ROOK_MASK[23][0][1] = 32768;
ROOK_MASK[23][0][2] = 2048;
ROOK_MASK[23][0][3] = 128;
ROOK_MASK[23][0][4] = 8;
ROOK_MASK[23][1] = new int[3];
ROOK_MASK[23][1][0] = 4194304;
ROOK_MASK[23][1][1] = 2097152;
ROOK_MASK[23][1][2] = 1048576;
}
// BISHOP_MASK Square, Ray, Step
public static final int[][][] BISHOP_MASK = new int[24][][];
static
{
BISHOP_MASK[0] = new int[1][];
BISHOP_MASK[0][0] = new int[3];
BISHOP_MASK[0][0][0] = 32;
BISHOP_MASK[0][0][1] = 1024;
BISHOP_MASK[0][0][2] = 32768;
BISHOP_MASK[1] = new int[2][];
BISHOP_MASK[1][0] = new int[2];
BISHOP_MASK[1][0][0] = 64;
BISHOP_MASK[1][0][1] = 2048;
BISHOP_MASK[1][1] = new int[1];
BISHOP_MASK[1][1][0] = 16;
BISHOP_MASK[2] = new int[2][];
BISHOP_MASK[2][0] = new int[1];
BISHOP_MASK[2][0][0] = 128;
BISHOP_MASK[2][1] = new int[2];
BISHOP_MASK[2][1][0] = 32;
BISHOP_MASK[2][1][1] = 256;
BISHOP_MASK[3] = new int[1][];
BISHOP_MASK[3][0] = new int[3];
BISHOP_MASK[3][0][0] = 64;
BISHOP_MASK[3][0][1] = 512;
BISHOP_MASK[3][0][2] = 4096;
BISHOP_MASK[4] = new int[2][];
BISHOP_MASK[4][0] = new int[3];
BISHOP_MASK[4][0][0] = 512;
BISHOP_MASK[4][0][1] = 16384;
BISHOP_MASK[4][0][2] = 524288;
BISHOP_MASK[4][1] = new int[1];
BISHOP_MASK[4][1][0] = 2;
BISHOP_MASK[5] = new int[4][];
BISHOP_MASK[5][0] = new int[2];
BISHOP_MASK[5][0][0] = 1024;
BISHOP_MASK[5][0][1] = 32768;
BISHOP_MASK[5][1] = new int[1];
BISHOP_MASK[5][1][0] = 256;
BISHOP_MASK[5][2] = new int[1];
BISHOP_MASK[5][2][0] = 1;
BISHOP_MASK[5][3] = new int[1];
BISHOP_MASK[5][3][0] = 4;
BISHOP_MASK[6] = new int[4][];
BISHOP_MASK[6][0] = new int[1];
BISHOP_MASK[6][0][0] = 2048;
BISHOP_MASK[6][1] = new int[2];
BISHOP_MASK[6][1][0] = 512;
BISHOP_MASK[6][1][1] = 4096;
BISHOP_MASK[6][2] = new int[1];
BISHOP_MASK[6][2][0] = 2;
BISHOP_MASK[6][3] = new int[1];
BISHOP_MASK[6][3][0] = 8;
BISHOP_MASK[7] = new int[2][];
BISHOP_MASK[7][0] = new int[3];
BISHOP_MASK[7][0][0] = 1024;
BISHOP_MASK[7][0][1] = 8192;
BISHOP_MASK[7][0][2] = 65536;
BISHOP_MASK[7][1] = new int[1];
BISHOP_MASK[7][1][0] = 4;
BISHOP_MASK[8] = new int[2][];
BISHOP_MASK[8][0] = new int[3];
BISHOP_MASK[8][0][0] = 8192;
BISHOP_MASK[8][0][1] = 262144;
BISHOP_MASK[8][0][2] = 8388608;
BISHOP_MASK[8][1] = new int[2];
BISHOP_MASK[8][1][0] = 32;
BISHOP_MASK[8][1][1] = 4;
BISHOP_MASK[9] = new int[4][];
BISHOP_MASK[9][0] = new int[2];
BISHOP_MASK[9][0][0] = 16384;
BISHOP_MASK[9][0][1] = 524288;
BISHOP_MASK[9][1] = new int[1];
BISHOP_MASK[9][1][0] = 4096;
BISHOP_MASK[9][2] = new int[1];
BISHOP_MASK[9][2][0] = 16;
BISHOP_MASK[9][3] = new int[2];
BISHOP_MASK[9][3][0] = 64;
BISHOP_MASK[9][3][1] = 8;
BISHOP_MASK[10] = new int[4][];
BISHOP_MASK[10][0] = new int[1];
BISHOP_MASK[10][0][0] = 32768;
BISHOP_MASK[10][1] = new int[2];
BISHOP_MASK[10][1][0] = 8192;
BISHOP_MASK[10][1][1] = 65536;
BISHOP_MASK[10][2] = new int[2];
BISHOP_MASK[10][2][0] = 32;
BISHOP_MASK[10][2][1] = 1;
BISHOP_MASK[10][3] = new int[1];
BISHOP_MASK[10][3][0] = 128;
BISHOP_MASK[11] = new int[2][];
BISHOP_MASK[11][0] = new int[3];
BISHOP_MASK[11][0][0] = 16384;
BISHOP_MASK[11][0][1] = 131072;
BISHOP_MASK[11][0][2] = 1048576;
BISHOP_MASK[11][1] = new int[2];
BISHOP_MASK[11][1][0] = 64;
BISHOP_MASK[11][1][1] = 2;
BISHOP_MASK[12] = new int[2][];
BISHOP_MASK[12][0] = new int[2];
BISHOP_MASK[12][0][0] = 131072;
BISHOP_MASK[12][0][1] = 4194304;
BISHOP_MASK[12][1] = new int[3];
BISHOP_MASK[12][1][0] = 512;
BISHOP_MASK[12][1][1] = 64;
BISHOP_MASK[12][1][2] = 8;
BISHOP_MASK[13] = new int[4][];
BISHOP_MASK[13][0] = new int[2];
BISHOP_MASK[13][0][0] = 262144;
BISHOP_MASK[13][0][1] = 8388608;
BISHOP_MASK[13][1] = new int[1];
BISHOP_MASK[13][1][0] = 65536;
BISHOP_MASK[13][2] = new int[1];
BISHOP_MASK[13][2][0] = 256;
BISHOP_MASK[13][3] = new int[2];
BISHOP_MASK[13][3][0] = 1024;
BISHOP_MASK[13][3][1] = 128;
BISHOP_MASK[14] = new int[4][];
BISHOP_MASK[14][0] = new int[1];
BISHOP_MASK[14][0][0] = 524288;
BISHOP_MASK[14][1] = new int[2];
BISHOP_MASK[14][1][0] = 131072;
BISHOP_MASK[14][1][1] = 1048576;
BISHOP_MASK[14][2] = new int[2];
BISHOP_MASK[14][2][0] = 512;
BISHOP_MASK[14][2][1] = 16;
BISHOP_MASK[14][3] = new int[1];
BISHOP_MASK[14][3][0] = 2048;
BISHOP_MASK[15] = new int[2][];
BISHOP_MASK[15][0] = new int[2];
BISHOP_MASK[15][0][0] = 262144;
BISHOP_MASK[15][0][1] = 2097152;
BISHOP_MASK[15][1] = new int[3];
BISHOP_MASK[15][1][0] = 1024;
BISHOP_MASK[15][1][1] = 32;
BISHOP_MASK[15][1][2] = 1;
BISHOP_MASK[16] = new int[2][];
BISHOP_MASK[16][0] = new int[1];
BISHOP_MASK[16][0][0] = 2097152;
BISHOP_MASK[16][1] = new int[3];
BISHOP_MASK[16][1][0] = 8192;
BISHOP_MASK[16][1][1] = 1024;
BISHOP_MASK[16][1][2] = 128;
BISHOP_MASK[17] = new int[4][];
BISHOP_MASK[17][0] = new int[1];
BISHOP_MASK[17][0][0] = 4194304;
BISHOP_MASK[17][1] = new int[1];
BISHOP_MASK[17][1][0] = 1048576;
BISHOP_MASK[17][2] = new int[1];
BISHOP_MASK[17][2][0] = 4096;
BISHOP_MASK[17][3] = new int[2];
BISHOP_MASK[17][3][0] = 16384;
BISHOP_MASK[17][3][1] = 2048;
BISHOP_MASK[18] = new int[4][];
BISHOP_MASK[18][0] = new int[1];
BISHOP_MASK[18][0][0] = 8388608;
BISHOP_MASK[18][1] = new int[1];
BISHOP_MASK[18][1][0] = 2097152;
BISHOP_MASK[18][2] = new int[2];
BISHOP_MASK[18][2][0] = 8192;
BISHOP_MASK[18][2][1] = 256;
BISHOP_MASK[18][3] = new int[1];
BISHOP_MASK[18][3][0] = 32768;
BISHOP_MASK[19] = new int[2][];
BISHOP_MASK[19][0] = new int[1];
BISHOP_MASK[19][0][0] = 4194304;
BISHOP_MASK[19][1] = new int[3];
BISHOP_MASK[19][1][0] = 16384;
BISHOP_MASK[19][1][1] = 512;
BISHOP_MASK[19][1][2] = 16;
BISHOP_MASK[20] = new int[1][];
BISHOP_MASK[20][0] = new int[3];
BISHOP_MASK[20][0][0] = 131072;
BISHOP_MASK[20][0][1] = 16384;
BISHOP_MASK[20][0][2] = 2048;
BISHOP_MASK[21] = new int[2][];
BISHOP_MASK[21][0] = new int[1];
BISHOP_MASK[21][0][0] = 65536;
BISHOP_MASK[21][1] = new int[2];
BISHOP_MASK[21][1][0] = 262144;
BISHOP_MASK[21][1][1] = 32768;
BISHOP_MASK[22] = new int[2][];
BISHOP_MASK[22][0] = new int[2];
BISHOP_MASK[22][0][0] = 131072;
BISHOP_MASK[22][0][1] = 4096;
BISHOP_MASK[22][1] = new int[1];
BISHOP_MASK[22][1][0] = 524288;
BISHOP_MASK[23] = new int[1][];
BISHOP_MASK[23][0] = new int[3];
BISHOP_MASK[23][0][0] = 262144;
BISHOP_MASK[23][0][1] = 8192;
BISHOP_MASK[23][0][2] = 256;
}
// Distance Map for Pieces
// Ordererd by King position, Piece position
public static final int[][] DISTANCE_MAP = new int[24][24];
static
{
DISTANCE_MAP[0][0] = 0;
DISTANCE_MAP[0][1] = 1;
DISTANCE_MAP[0][2] = 51;
DISTANCE_MAP[0][3] = 1911;
DISTANCE_MAP[0][4] = 1;
DISTANCE_MAP[0][5] = 19;
DISTANCE_MAP[0][6] = 311;
DISTANCE_MAP[0][7] = 6015;
DISTANCE_MAP[0][8] = 51;
DISTANCE_MAP[0][9] = 311;
DISTANCE_MAP[0][10] = 887;
DISTANCE_MAP[0][11] = 14335;
DISTANCE_MAP[0][12] = 1911;
DISTANCE_MAP[0][13] = 6015;
DISTANCE_MAP[0][14] = 14335;
DISTANCE_MAP[0][15] = 229375;
DISTANCE_MAP[0][16] = 32767;
DISTANCE_MAP[0][17] = 98303;
DISTANCE_MAP[0][18] = 262143;
DISTANCE_MAP[0][19] = 524287;
DISTANCE_MAP[0][20] = 524287;
DISTANCE_MAP[0][21] = 2097151;
DISTANCE_MAP[0][22] = 4194303;
DISTANCE_MAP[0][23] = 8388607;
DISTANCE_MAP[1][0] = 2;
DISTANCE_MAP[1][1] = 0;
DISTANCE_MAP[1][2] = 2;
DISTANCE_MAP[1][3] = 119;
DISTANCE_MAP[1][4] = 39;
DISTANCE_MAP[1][5] = 2;
DISTANCE_MAP[1][6] = 39;
DISTANCE_MAP[1][7] = 639;
DISTANCE_MAP[1][8] = 639;
DISTANCE_MAP[1][9] = 119;
DISTANCE_MAP[1][10] = 639;
DISTANCE_MAP[1][11] = 2047;
DISTANCE_MAP[1][12] = 12287;
DISTANCE_MAP[1][13] = 4095;
DISTANCE_MAP[1][14] = 12287;
DISTANCE_MAP[1][15] = 32767;
DISTANCE_MAP[1][16] = 196607;
DISTANCE_MAP[1][17] = 65535;
DISTANCE_MAP[1][18] = 196607;
DISTANCE_MAP[1][19] = 524287;
DISTANCE_MAP[1][20] = 3145727;
DISTANCE_MAP[1][21] = 1048575;
DISTANCE_MAP[1][22] = 3145727;
DISTANCE_MAP[1][23] = 8388607;
DISTANCE_MAP[2][0] = 238;
DISTANCE_MAP[2][1] = 4;
DISTANCE_MAP[2][2] = 0;
DISTANCE_MAP[2][3] = 4;
DISTANCE_MAP[2][4] = 1263;
DISTANCE_MAP[2][5] = 78;
DISTANCE_MAP[2][6] = 4;
DISTANCE_MAP[2][7] = 78;
DISTANCE_MAP[2][8] = 3839;
DISTANCE_MAP[2][9] = 1263;
DISTANCE_MAP[2][10] = 238;
DISTANCE_MAP[2][11] = 1263;
DISTANCE_MAP[2][12] = 61439;
DISTANCE_MAP[2][13] = 20479;
DISTANCE_MAP[2][14] = 4095;
DISTANCE_MAP[2][15] = 20479;
DISTANCE_MAP[2][16] = 983039;
DISTANCE_MAP[2][17] = 327679;
DISTANCE_MAP[2][18] = 65535;
DISTANCE_MAP[2][19] = 327679;
DISTANCE_MAP[2][20] = 15728639;
DISTANCE_MAP[2][21] = 5242879;
DISTANCE_MAP[2][22] = 1048575;
DISTANCE_MAP[2][23] = 5242879;
DISTANCE_MAP[3][0] = 3822;
DISTANCE_MAP[3][1] = 204;
DISTANCE_MAP[3][2] = 8;
DISTANCE_MAP[3][3] = 0;
DISTANCE_MAP[3][4] = 36591;
DISTANCE_MAP[3][5] = 2254;
DISTANCE_MAP[3][6] = 140;
DISTANCE_MAP[3][7] = 8;
DISTANCE_MAP[3][8] = 52991;
DISTANCE_MAP[3][9] = 3310;
DISTANCE_MAP[3][10] = 2254;
DISTANCE_MAP[3][11] = 204;
DISTANCE_MAP[3][12] = 847871;
DISTANCE_MAP[3][13] = 52991;
DISTANCE_MAP[3][14] = 36591;
DISTANCE_MAP[3][15] = 3822;
DISTANCE_MAP[3][16] = 983039;
DISTANCE_MAP[3][17] = 851967;
DISTANCE_MAP[3][18] = 585727;
DISTANCE_MAP[3][19] = 61439;
DISTANCE_MAP[3][20] = 15728639;
DISTANCE_MAP[3][21] = 13631487;
DISTANCE_MAP[3][22] = 9437183;
DISTANCE_MAP[3][23] = 983039;
DISTANCE_MAP[4][0] = 16;
DISTANCE_MAP[4][1] = 305;
DISTANCE_MAP[4][2] = 4979;
DISTANCE_MAP[4][3] = 96247;
DISTANCE_MAP[4][4] = 0;
DISTANCE_MAP[4][5] = 16;
DISTANCE_MAP[4][6] = 819;
DISTANCE_MAP[4][7] = 30583;
DISTANCE_MAP[4][8] = 16;
DISTANCE_MAP[4][9] = 305;
DISTANCE_MAP[4][10] = 4979;
DISTANCE_MAP[4][11] = 96247;
DISTANCE_MAP[4][12] = 819;
DISTANCE_MAP[4][13] = 4979;
DISTANCE_MAP[4][14] = 14199;
DISTANCE_MAP[4][15] = 229375;
DISTANCE_MAP[4][16] = 30583;
DISTANCE_MAP[4][17] = 96247;
DISTANCE_MAP[4][18] = 229375;
DISTANCE_MAP[4][19] = 3670015;
DISTANCE_MAP[4][20] = 524287;
DISTANCE_MAP[4][21] = 1572863;
DISTANCE_MAP[4][22] = 4194303;
DISTANCE_MAP[4][23] = 8388607;
DISTANCE_MAP[5][0] = 626;
DISTANCE_MAP[5][1] = 32;
DISTANCE_MAP[5][2] = 626;
DISTANCE_MAP[5][3] = 10231;
DISTANCE_MAP[5][4] = 32;
DISTANCE_MAP[5][5] = 0;
DISTANCE_MAP[5][6] = 32;
DISTANCE_MAP[5][7] = 1911;
DISTANCE_MAP[5][8] = 626;
DISTANCE_MAP[5][9] = 32;
DISTANCE_MAP[5][10] = 626;
DISTANCE_MAP[5][11] = 10231;
DISTANCE_MAP[5][12] = 10231;
DISTANCE_MAP[5][13] = 1911;
DISTANCE_MAP[5][14] = 10231;
DISTANCE_MAP[5][15] = 32767;
DISTANCE_MAP[5][16] = 196607;
DISTANCE_MAP[5][17] = 65535;
DISTANCE_MAP[5][18] = 196607;
DISTANCE_MAP[5][19] = 524287;
DISTANCE_MAP[5][20] = 3145727;
DISTANCE_MAP[5][21] = 1048575;
DISTANCE_MAP[5][22] = 3145727;
DISTANCE_MAP[5][23] = 8388607;
DISTANCE_MAP[6][0] = 20222;
DISTANCE_MAP[6][1] = 1252;
DISTANCE_MAP[6][2] = 64;
DISTANCE_MAP[6][3] = 1252;
DISTANCE_MAP[6][4] = 3822;
DISTANCE_MAP[6][5] = 64;
DISTANCE_MAP[6][6] = 0;
DISTANCE_MAP[6][7] = 64;
DISTANCE_MAP[6][8] = 20222;
DISTANCE_MAP[6][9] = 1252;
DISTANCE_MAP[6][10] = 64;
DISTANCE_MAP[6][11] = 1252;
DISTANCE_MAP[6][12] = 61439;
DISTANCE_MAP[6][13] = 20222;
DISTANCE_MAP[6][14] = 3822;
DISTANCE_MAP[6][15] = 20222;
DISTANCE_MAP[6][16] = 983039;
DISTANCE_MAP[6][17] = 327679;
DISTANCE_MAP[6][18] = 65535;
DISTANCE_MAP[6][19] = 327679;
DISTANCE_MAP[6][20] = 15728639;
DISTANCE_MAP[6][21] = 5242879;
DISTANCE_MAP[6][22] = 1048575;
DISTANCE_MAP[6][23] = 5242879;
DISTANCE_MAP[7][0] = 585470;
DISTANCE_MAP[7][1] = 36076;
DISTANCE_MAP[7][2] = 2248;
DISTANCE_MAP[7][3] = 128;
DISTANCE_MAP[7][4] = 61166;
DISTANCE_MAP[7][5] = 3276;
DISTANCE_MAP[7][6] = 128;
DISTANCE_MAP[7][7] = 0;
DISTANCE_MAP[7][8] = 585470;
DISTANCE_MAP[7][9] = 36076;
DISTANCE_MAP[7][10] = 2248;
DISTANCE_MAP[7][11] = 128;
DISTANCE_MAP[7][12] = 847871;
DISTANCE_MAP[7][13] = 52974;
DISTANCE_MAP[7][14] = 36076;
DISTANCE_MAP[7][15] = 3276;
DISTANCE_MAP[7][16] = 13565951;
DISTANCE_MAP[7][17] = 847871;
DISTANCE_MAP[7][18] = 585470;
DISTANCE_MAP[7][19] = 61166;
DISTANCE_MAP[7][20] = 15728639;
DISTANCE_MAP[7][21] = 13631487;
DISTANCE_MAP[7][22] = 9371647;
DISTANCE_MAP[7][23] = 983039;
DISTANCE_MAP[8][0] = 13104;
DISTANCE_MAP[8][1] = 79665;
DISTANCE_MAP[8][2] = 227187;
DISTANCE_MAP[8][3] = 3670007;
DISTANCE_MAP[8][4] = 256;
DISTANCE_MAP[8][5] = 4880;
DISTANCE_MAP[8][6] = 79665;
DISTANCE_MAP[8][7] = 1539959;
DISTANCE_MAP[8][8] = 0;
DISTANCE_MAP[8][9] = 256;
DISTANCE_MAP[8][10] = 13104;
DISTANCE_MAP[8][11] = 489335;
DISTANCE_MAP[8][12] = 256;
DISTANCE_MAP[8][13] = 4880;
DISTANCE_MAP[8][14] = 79665;
DISTANCE_MAP[8][15] = 1539959;
DISTANCE_MAP[8][16] = 13104;
DISTANCE_MAP[8][17] = 79665;
DISTANCE_MAP[8][18] = 227187;
DISTANCE_MAP[8][19] = 3670007;
DISTANCE_MAP[8][20] = 489335;
DISTANCE_MAP[8][21] = 1539959;
DISTANCE_MAP[8][22] = 3670007;
DISTANCE_MAP[8][23] = 8388607;
DISTANCE_MAP[9][0] = 163698;
DISTANCE_MAP[9][1] = 30576;
DISTANCE_MAP[9][2] = 163698;
DISTANCE_MAP[9][3] = 524279;
DISTANCE_MAP[9][4] = 10016;
DISTANCE_MAP[9][5] = 512;
DISTANCE_MAP[9][6] = 10016;
DISTANCE_MAP[9][7] = 163698;
DISTANCE_MAP[9][8] = 512;
DISTANCE_MAP[9][9] = 0;
DISTANCE_MAP[9][10] = 512;
DISTANCE_MAP[9][11] = 30576;
DISTANCE_MAP[9][12] = 10016;
DISTANCE_MAP[9][13] = 512;
DISTANCE_MAP[9][14] = 10016;
DISTANCE_MAP[9][15] = 163698;
DISTANCE_MAP[9][16] = 163698;
DISTANCE_MAP[9][17] = 30576;
DISTANCE_MAP[9][18] = 163698;
DISTANCE_MAP[9][19] = 524279;
DISTANCE_MAP[9][20] = 3145727;
DISTANCE_MAP[9][21] = 1048575;
DISTANCE_MAP[9][22] = 3145727;
DISTANCE_MAP[9][23] = 8388607;
DISTANCE_MAP[10][0] = 983038;
DISTANCE_MAP[10][1] = 323556;
DISTANCE_MAP[10][2] = 61152;
DISTANCE_MAP[10][3] = 323556;
DISTANCE_MAP[10][4] = 323556;
DISTANCE_MAP[10][5] = 20032;
DISTANCE_MAP[10][6] = 1024;
DISTANCE_MAP[10][7] = 20032;
DISTANCE_MAP[10][8] = 61152;
DISTANCE_MAP[10][9] = 1024;
DISTANCE_MAP[10][10] = 0;
DISTANCE_MAP[10][11] = 1024;
DISTANCE_MAP[10][12] = 323556;
DISTANCE_MAP[10][13] = 20032;
DISTANCE_MAP[10][14] = 1024;
DISTANCE_MAP[10][15] = 20032;
DISTANCE_MAP[10][16] = 983038;
DISTANCE_MAP[10][17] = 323556;
DISTANCE_MAP[10][18] = 61152;
DISTANCE_MAP[10][19] = 323556;
DISTANCE_MAP[10][20] = 15728639;
DISTANCE_MAP[10][21] = 5242879;
DISTANCE_MAP[10][22] = 1048575;
DISTANCE_MAP[10][23] = 5242879;
DISTANCE_MAP[11][0] = 13565950;
DISTANCE_MAP[11][1] = 847596;
DISTANCE_MAP[11][2] = 577224;
DISTANCE_MAP[11][3] = 52416;
DISTANCE_MAP[11][4] = 9367534;
DISTANCE_MAP[11][5] = 577224;
DISTANCE_MAP[11][6] = 35968;
DISTANCE_MAP[11][7] = 2048;
DISTANCE_MAP[11][8] = 978670;
DISTANCE_MAP[11][9] = 52416;
DISTANCE_MAP[11][10] = 2048;
DISTANCE_MAP[11][11] = 0;
DISTANCE_MAP[11][12] = 9367534;
DISTANCE_MAP[11][13] = 577224;
DISTANCE_MAP[11][14] = 35968;
DISTANCE_MAP[11][15] = 2048;
DISTANCE_MAP[11][16] = 13565950;
DISTANCE_MAP[11][17] = 847596;
DISTANCE_MAP[11][18] = 577224;
DISTANCE_MAP[11][19] = 52416;
DISTANCE_MAP[11][20] = 15728639;
DISTANCE_MAP[11][21] = 13565950;
DISTANCE_MAP[11][22] = 9367534;
DISTANCE_MAP[11][23] = 978670;
DISTANCE_MAP[12][0] = 7829360;
DISTANCE_MAP[12][1] = 7862129;
DISTANCE_MAP[12][2] = 8388467;
DISTANCE_MAP[12][3] = 16777207;
DISTANCE_MAP[12][4] = 209664;
DISTANCE_MAP[12][5] = 1274640;
DISTANCE_MAP[12][6] = 3634992;
DISTANCE_MAP[12][7] = 8388467;
DISTANCE_MAP[12][8] = 4096;
DISTANCE_MAP[12][9] = 78080;
DISTANCE_MAP[12][10] = 1274640;
DISTANCE_MAP[12][11] = 7862129;
DISTANCE_MAP[12][12] = 0;
DISTANCE_MAP[12][13] = 4096;
DISTANCE_MAP[12][14] = 209664;
DISTANCE_MAP[12][15] = 7829360;
DISTANCE_MAP[12][16] = 4096;
DISTANCE_MAP[12][17] = 78080;
DISTANCE_MAP[12][18] = 1274640;
DISTANCE_MAP[12][19] = 7862129;
DISTANCE_MAP[12][20] = 209664;
DISTANCE_MAP[12][21] = 1274640;
DISTANCE_MAP[12][22] = 3634992;
DISTANCE_MAP[12][23] = 8388467;
DISTANCE_MAP[13][0] = 16777202;
DISTANCE_MAP[13][1] = 16777200;
DISTANCE_MAP[13][2] = 16777202;
DISTANCE_MAP[13][3] = 16777207;
DISTANCE_MAP[13][4] = 2619168;
DISTANCE_MAP[13][5] = 489216;
DISTANCE_MAP[13][6] = 2619168;
DISTANCE_MAP[13][7] = 8388464;
DISTANCE_MAP[13][8] = 160256;
DISTANCE_MAP[13][9] = 8192;
DISTANCE_MAP[13][10] = 160256;
DISTANCE_MAP[13][11] = 2619168;
DISTANCE_MAP[13][12] = 8192;
DISTANCE_MAP[13][13] = 0;
DISTANCE_MAP[13][14] = 8192;
DISTANCE_MAP[13][15] = 489216;
DISTANCE_MAP[13][16] = 160256;
DISTANCE_MAP[13][17] = 8192;
DISTANCE_MAP[13][18] = 160256;
DISTANCE_MAP[13][19] = 2619168;
DISTANCE_MAP[13][20] = 2619168;
DISTANCE_MAP[13][21] = 489216;
DISTANCE_MAP[13][22] = 2619168;
DISTANCE_MAP[13][23] = 8388464;
DISTANCE_MAP[14][0] = 16777214;
DISTANCE_MAP[14][1] = 16777204;
DISTANCE_MAP[14][2] = 16777200;
DISTANCE_MAP[14][3] = 16777204;
DISTANCE_MAP[14][4] = 15728608;
DISTANCE_MAP[14][5] = 5176896;
DISTANCE_MAP[14][6] = 978432;
DISTANCE_MAP[14][7] = 5176896;
DISTANCE_MAP[14][8] = 5176896;
DISTANCE_MAP[14][9] = 320512;
DISTANCE_MAP[14][10] = 16384;
DISTANCE_MAP[14][11] = 320512;
DISTANCE_MAP[14][12] = 978432;
DISTANCE_MAP[14][13] = 16384;
DISTANCE_MAP[14][14] = 0;
DISTANCE_MAP[14][15] = 16384;
DISTANCE_MAP[14][16] = 5176896;
DISTANCE_MAP[14][17] = 320512;
DISTANCE_MAP[14][18] = 16384;
DISTANCE_MAP[14][19] = 320512;
DISTANCE_MAP[14][20] = 15728608;
DISTANCE_MAP[14][21] = 5176896;
DISTANCE_MAP[14][22] = 978432;
DISTANCE_MAP[14][23] = 5176896;
DISTANCE_MAP[15][0] = 16777214;
DISTANCE_MAP[15][1] = 15728620;
DISTANCE_MAP[15][2] = 15662824;
DISTANCE_MAP[15][3] = 15658720;
DISTANCE_MAP[15][4] = 15728620;
DISTANCE_MAP[15][5] = 13561536;
DISTANCE_MAP[15][6] = 9235584;
DISTANCE_MAP[15][7] = 838656;
DISTANCE_MAP[15][8] = 15662824;
DISTANCE_MAP[15][9] = 9235584;
DISTANCE_MAP[15][10] = 575488;
DISTANCE_MAP[15][11] = 32768;
DISTANCE_MAP[15][12] = 15658720;
DISTANCE_MAP[15][13] = 838656;
DISTANCE_MAP[15][14] = 32768;
DISTANCE_MAP[15][15] = 0;
DISTANCE_MAP[15][16] = 15662824;
DISTANCE_MAP[15][17] = 9235584;
DISTANCE_MAP[15][18] = 575488;
DISTANCE_MAP[15][19] = 32768;
DISTANCE_MAP[15][20] = 15728620;
DISTANCE_MAP[15][21] = 13561536;
DISTANCE_MAP[15][22] = 9235584;
DISTANCE_MAP[15][23] = 838656;
DISTANCE_MAP[16][0] = 16777072;
DISTANCE_MAP[16][1] = 16777073;
DISTANCE_MAP[16][2] = 16777203;
DISTANCE_MAP[16][3] = 16777207;
DISTANCE_MAP[16][4] = 7829248;
DISTANCE_MAP[16][5] = 8353552;
DISTANCE_MAP[16][6] = 16774960;
DISTANCE_MAP[16][7] = 16777075;
DISTANCE_MAP[16][8] = 3354624;
DISTANCE_MAP[16][9] = 3617024;
DISTANCE_MAP[16][10] = 7828224;
DISTANCE_MAP[16][11] = 16774960;
DISTANCE_MAP[16][12] = 65536;
DISTANCE_MAP[16][13] = 1249280;
DISTANCE_MAP[16][14] = 3617024;
DISTANCE_MAP[16][15] = 8353552;
DISTANCE_MAP[16][16] = 0;
DISTANCE_MAP[16][17] = 65536;
DISTANCE_MAP[16][18] = 3354624;
DISTANCE_MAP[16][19] = 7829248;
DISTANCE_MAP[16][20] = 65536;
DISTANCE_MAP[16][21] = 1249280;
DISTANCE_MAP[16][22] = 3617024;
DISTANCE_MAP[16][23] = 8353552;
DISTANCE_MAP[17][0] = 16777202;
DISTANCE_MAP[17][1] = 16777200;
DISTANCE_MAP[17][2] = 16777202;
DISTANCE_MAP[17][3] = 16777207;
DISTANCE_MAP[17][4] = 16776992;
DISTANCE_MAP[17][5] = 16776960;
DISTANCE_MAP[17][6] = 16776992;
DISTANCE_MAP[17][7] = 16777072;
DISTANCE_MAP[17][8] = 8352256;
DISTANCE_MAP[17][9] = 7827456;
DISTANCE_MAP[17][10] = 8352256;
DISTANCE_MAP[17][11] = 16774912;
DISTANCE_MAP[17][12] = 2564096;
DISTANCE_MAP[17][13] = 131072;
DISTANCE_MAP[17][14] = 2564096;
DISTANCE_MAP[17][15] = 8352256;
DISTANCE_MAP[17][16] = 131072;
DISTANCE_MAP[17][17] = 0;
DISTANCE_MAP[17][18] = 131072;
DISTANCE_MAP[17][19] = 7827456;
DISTANCE_MAP[17][20] = 2564096;
DISTANCE_MAP[17][21] = 131072;
DISTANCE_MAP[17][22] = 2564096;
DISTANCE_MAP[17][23] = 8352256;
DISTANCE_MAP[18][0] = 16777214;
DISTANCE_MAP[18][1] = 16777204;
DISTANCE_MAP[18][2] = 16777200;
DISTANCE_MAP[18][3] = 16777204;
DISTANCE_MAP[18][4] = 16777184;
DISTANCE_MAP[18][5] = 16777024;
DISTANCE_MAP[18][6] = 16776960;
DISTANCE_MAP[18][7] = 16777024;
DISTANCE_MAP[18][8] = 16776704;
DISTANCE_MAP[18][9] = 15721472;
DISTANCE_MAP[18][10] = 15654912;
DISTANCE_MAP[18][11] = 15721472;
DISTANCE_MAP[18][12] = 15721472;
DISTANCE_MAP[18][13] = 5128192;
DISTANCE_MAP[18][14] = 262144;
DISTANCE_MAP[18][15] = 5128192;
DISTANCE_MAP[18][16] = 15654912;
DISTANCE_MAP[18][17] = 262144;
DISTANCE_MAP[18][18] = 0;
DISTANCE_MAP[18][19] = 262144;
DISTANCE_MAP[18][20] = 15721472;
DISTANCE_MAP[18][21] = 5128192;
DISTANCE_MAP[18][22] = 262144;
DISTANCE_MAP[18][23] = 5128192;
DISTANCE_MAP[19][0] = 16777214;
DISTANCE_MAP[19][1] = 16777212;
DISTANCE_MAP[19][2] = 16777192;
DISTANCE_MAP[19][3] = 16777184;
DISTANCE_MAP[19][4] = 16777196;
DISTANCE_MAP[19][5] = 16776896;
DISTANCE_MAP[19][6] = 15724160;
DISTANCE_MAP[19][7] = 15658496;
DISTANCE_MAP[19][8] = 16776896;
DISTANCE_MAP[19][9] = 15657984;
DISTANCE_MAP[19][10] = 13551616;
DISTANCE_MAP[19][11] = 13418496;
DISTANCE_MAP[19][12] = 15724160;
DISTANCE_MAP[19][13] = 13551616;
DISTANCE_MAP[19][14] = 9207808;
DISTANCE_MAP[19][15] = 524288;
DISTANCE_MAP[19][16] = 15658496;
DISTANCE_MAP[19][17] = 13418496;
DISTANCE_MAP[19][18] = 524288;
DISTANCE_MAP[19][19] = 0;
DISTANCE_MAP[19][20] = 15724160;
DISTANCE_MAP[19][21] = 13551616;
DISTANCE_MAP[19][22] = 9207808;
DISTANCE_MAP[19][23] = 524288;
DISTANCE_MAP[20][0] = 16777072;
DISTANCE_MAP[20][1] = 16777201;
DISTANCE_MAP[20][2] = 16777203;
DISTANCE_MAP[20][3] = 16777207;
DISTANCE_MAP[20][4] = 16774912;
DISTANCE_MAP[20][5] = 16774928;
DISTANCE_MAP[20][6] = 16777008;
DISTANCE_MAP[20][7] = 16777072;
DISTANCE_MAP[20][8] = 7827456;
DISTANCE_MAP[20][9] = 16216320;
DISTANCE_MAP[20][10] = 16741120;
DISTANCE_MAP[20][11] = 16774960;
DISTANCE_MAP[20][12] = 3342336;
DISTANCE_MAP[20][13] = 7540736;
DISTANCE_MAP[20][14] = 7811072;
DISTANCE_MAP[20][15] = 16741120;
DISTANCE_MAP[20][16] = 1048576;
DISTANCE_MAP[20][17] = 3211264;
DISTANCE_MAP[20][18] = 7540736;
DISTANCE_MAP[20][19] = 16216320;
DISTANCE_MAP[20][20] = 0;
DISTANCE_MAP[20][21] = 1048576;
DISTANCE_MAP[20][22] = 3342336;
DISTANCE_MAP[20][23] = 7827456;
DISTANCE_MAP[21][0] = 16777202;
DISTANCE_MAP[21][1] = 16777200;
DISTANCE_MAP[21][2] = 16777202;
DISTANCE_MAP[21][3] = 16777207;
DISTANCE_MAP[21][4] = 16776992;
DISTANCE_MAP[21][5] = 16776960;
DISTANCE_MAP[21][6] = 16776992;
DISTANCE_MAP[21][7] = 16777072;
DISTANCE_MAP[21][8] = 16773632;
DISTANCE_MAP[21][9] = 16773120;
DISTANCE_MAP[21][10] = 16773632;
DISTANCE_MAP[21][11] = 16774912;
DISTANCE_MAP[21][12] = 16195584;
DISTANCE_MAP[21][13] = 7798784;
DISTANCE_MAP[21][14] = 16195584;
DISTANCE_MAP[21][15] = 16740352;
DISTANCE_MAP[21][16] = 7471104;
DISTANCE_MAP[21][17] = 2097152;
DISTANCE_MAP[21][18] = 7471104;
DISTANCE_MAP[21][19] = 16195584;
DISTANCE_MAP[21][20] = 2097152;
DISTANCE_MAP[21][21] = 0;
DISTANCE_MAP[21][22] = 2097152;
DISTANCE_MAP[21][23] = 7798784;
DISTANCE_MAP[22][0] = 16777214;
DISTANCE_MAP[22][1] = 16777204;
DISTANCE_MAP[22][2] = 16777200;
DISTANCE_MAP[22][3] = 16777204;
DISTANCE_MAP[22][4] = 16777184;
DISTANCE_MAP[22][5] = 16777024;
DISTANCE_MAP[22][6] = 16776960;
DISTANCE_MAP[22][7] = 16777024;
DISTANCE_MAP[22][8] = 16776704;
DISTANCE_MAP[22][9] = 16774144;
DISTANCE_MAP[22][10] = 16773120;
DISTANCE_MAP[22][11] = 16774144;
DISTANCE_MAP[22][12] = 16769024;
DISTANCE_MAP[22][13] = 16662528;
DISTANCE_MAP[22][14] = 15597568;
DISTANCE_MAP[22][15] = 16662528;
DISTANCE_MAP[22][16] = 16662528;
DISTANCE_MAP[22][17] = 14942208;
DISTANCE_MAP[22][18] = 4194304;
DISTANCE_MAP[22][19] = 14942208;
DISTANCE_MAP[22][20] = 15597568;
DISTANCE_MAP[22][21] = 4194304;
DISTANCE_MAP[22][22] = 0;
DISTANCE_MAP[22][23] = 4194304;
DISTANCE_MAP[23][0] = 16777214;
DISTANCE_MAP[23][1] = 16777212;
DISTANCE_MAP[23][2] = 16777208;
DISTANCE_MAP[23][3] = 16777184;
DISTANCE_MAP[23][4] = 16777184;
DISTANCE_MAP[23][5] = 16777152;
DISTANCE_MAP[23][6] = 16776832;
DISTANCE_MAP[23][7] = 16776704;
DISTANCE_MAP[23][8] = 16776896;
DISTANCE_MAP[23][9] = 16772096;
DISTANCE_MAP[23][10] = 16705536;
DISTANCE_MAP[23][11] = 15654912;
DISTANCE_MAP[23][12] = 16772096;
DISTANCE_MAP[23][13] = 15646720;
DISTANCE_MAP[23][14] = 15499264;
DISTANCE_MAP[23][15] = 13369344;
DISTANCE_MAP[23][16] = 16705536;
DISTANCE_MAP[23][17] = 15499264;
DISTANCE_MAP[23][18] = 13107200;
DISTANCE_MAP[23][19] = 8388608;
DISTANCE_MAP[23][20] = 15654912;
DISTANCE_MAP[23][21] = 13369344;
DISTANCE_MAP[23][22] = 8388608;
DISTANCE_MAP[23][23] = 0;
}
