val input = arrayOf(
    1587,
    1407,
    1717,
    1596,
    1566,
    1752,
    1925,
    1847,
    1716,
    1726,
    1611,
    1628,
    1853,
    1864,
    1831,
    1942,
    1634,
    1964,
    1603,
    1676,
    1256,
    1906,
    1655,
    1790,
    1666,
    1470,
    1540,
    1544,
    1100,
    1447,
    1384,
    1464,
    1651,
    1572,
    907,
    1653,
    1265,
    1510,
    1639,
    1818,
    376,
    1378,
    1132,
    1750,
    1491,
    1788,
    1882,
    1779,
    1640,
    1586,
    1525,
    1458,
    1994,
    1782,
    1412,
    1033,
    1416,
    1813,
    1520,
    1968,
    715,
    1396,
    1745,
    1506,
    1024,
    1798,
    1870,
    1615,
    1957,
    1718,
    1349,
    1983,
    1387,
    1738,
    1588,
    1321,
    1160,
    1907,
    1861,
    1940,
    1475,
    2004,
    1852,
    1760,
    1608,
    1028,
    1820,
    1495,
    1811,
    1737,
    1417,
    1316,
    1087,
    1803,
    1595,
    1346,
    1971,
    1692,
    1678,
    1330,
    1480,
    1097,
    1898,
    1973,
    1567,
    1733,
    1336,
    1381,
    1327,
    1670,
    1436,
    1989,
    1334,
    89,
    1862,
    1715,
    1743,
    1967,
    1765,
    1402,
    1729,
    1749,
    1671,
    1196,
    1650,
    1089,
    1814,
    1783,
    1225,
    1823,
    1746,
    2009,
    1886,
    1748,
    1481,
    1739,
    1912,
    1663,
    1668,
    1314,
    1594,
    705,
    1449,
    1731,
    1487,
    1648,
    1466,
    1317,
    1979,
    1799,
    1926,
    1703,
    1656,
    1978,
    2005,
    1865,
    1982,
    1951,
    1892,
    1713,
    1744,
    1598,
    1606,
    1583,
    1895,
    1804,
    1430,
    1816,
    1364,
    1575,
    1918,
    1431,
    1812,
    1471,
    1797,
    928,
    1934,
    1156,
    94,
    1563,
    1909,
    1453,
    1392,
    1427,
    1819,
    1524,
    1695,
    1866,
    2008,
    1413,
    1698,
    1051,
    1707,
    1904,
    1681,
    1541,
    1621,
    1421,
    1809,
    1576
)

val testInput = arrayOf(
    1721,
    979,
    366,
    299,
    675,
    1456
)

fun solvePuzzle1(): Int {
    val actualInput = input

    for (y in actualInput) {
        for (x in actualInput) {
            if (x == y) continue

            if (y + x == 2020) {
                return y * x
            }
        }
    }

    return -1
}

fun solvePuzzle1dot1(): Int {
    val actualInput = input

    for (z in actualInput) {
        for (y in actualInput) {
            for (x in actualInput) {
                if (z + y + x == 2020) {
                    return z * y * x
                }
            }
        }
    }

    return -1
}

fun generic(): Int {
    val actualInput = testInput
    val nrIterations = 3
    val goal = 2020

    var acum = mutableListOf<List<Int>>()

    for (i in 0 until nrIterations) {
        if (acum.isEmpty()) {
            for (x in actualInput) {
                acum.add(arrayListOf(x))
            }
        } else {
            val newAcum = arrayListOf<List<Int>>()
            for (x in actualInput) {
                for (a in acum) {
                    val temp = arrayListOf<Int>()
                    for(it in a) {
                        temp.add(it)
                    }
                    temp.add(x)

                    newAcum.add(temp)
                }
            }
            acum = newAcum
        }
    }

    for (a in acum) {
        var sum = 0
        var mult = 1
        for (x in a) {
            sum += x
            mult *= x
        }
        if (sum == goal) {
            return mult
        }
    }

    return -1
}

