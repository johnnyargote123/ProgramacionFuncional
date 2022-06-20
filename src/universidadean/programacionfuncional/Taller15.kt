package universidadean.programacionfuncional

import ean.collections.IList
import kotlin.math.pow
import kotlin.math.sqrt

// Esta clase guarda la información de un producto de una tienda
data class Producto(val codigo: Int, val nombre: String, val cantidad: Int, val precio: Int)

// Esta clase guarda la información de un departamento del país
data class Departamento(
    val nombre: String,
    val poblacion: Int,
    val superficie: Double,
    val densidad: Double,
    val IDH: Double,
    val añoCreacion: Int)

// Esta clase guarda la información de un municipio del pais
data class Municipio(
    val codigo: Int,
    val nombre: String,
    val departamento: String,
    val poblacionUrbana: Int,
    val poblacionRural: Int,
    val esCapital: Boolean
)

// Esta clase guarda la información de un rectángulo
data class Rectangulo(val base: Double, val altura: Double) {
    // Hallar el área del rectangulo
    fun area(): Double = base * altura
}

// Esta clase guarda la información de un triángulo
data class Triangulo(val id: Int,
                     val lado1: Double,
                     val lado2: Double,
                     val lado3: Double)

//-------------------------------------------------------------------
// Operaciones con la clase Departamento
//-------------------------------------------------------------------

/**
 * Obtener el nombre del departamento más antiguo de toda la lista.
 * Si la lista está vacía, retorne null
 */
fun metodo6(dptos: IList<Departamento>): String? {
    if (dptos.isEmpty)
        return null
    val departamento_mas_antiguo = dptos.minWith(compareBy(Departamento::añoCreacion))
    return departamento_mas_antiguo!!.nombre
}

/**
 * Retorna el  departamento que tiene la superficie más grande
 * pero con una población superior a la población que se pasa
 * como parámetro.
 */
fun metodo7(dptos: IList<Departamento>, poblacion: Int): Departamento? {
    //filtrado----------------------------------------------------------------------------------------------------------
    val poblacion_mayor_al_parametro = dptos.filter { it.poblacion > poblacion }

    // maxWith para comparar los departamentos filtrados----------------------------------------------------------------
    val departamento_superficie_mas_grande = poblacion_mayor_al_parametro.maxWith(compareBy(Departamento::superficie))

    return departamento_superficie_mas_grande
}

/**
 * Retorne la lista de los nombres de los departamentos creados
 * en el siglo XX y que tenga un IDH entre 0.85 y 0.95
 */
fun metodo8(dptos: IList<Departamento>): IList<String> {
    //filtrado por siglo -----------------------------------------------------------------------------------------------
    var departamentos_siglo = dptos.filter { it.añoCreacion in 1901.. 2000 }
    //filtrado IDH -----------------------------------------------------------------------------------------------------
    var Idh_rango_pedido = departamentos_siglo.filter { it.IDH in 0.85 .. 0.95 }
    // lista -----------------------------------------------------------------------------------------------------------
    var nombres_departamentos = Idh_rango_pedido.map { it.nombre }
    return nombres_departamentos
}

/**
 * Retorne el porcentaje de departamentos de la lista cuya densidad
 * esté por debajo del valor que se pasa como parámetro
 */
fun metodo9(deptos: IList<Departamento>, valor: Double): Double {
    //cuenta una cantidad de una lista ---------------------------------------------------------------------------------
    val cantidad=deptos.count()
    //otro tipo de filtrado (conteo) -----------------------------------------------------------------------------------
    val porcentaje= deptos.count { it.densidad < valor }
    return 100*porcentaje.toDouble()/cantidad
}

/**
 * Retorne el promedio de superficie de los departamentos de la lista
 * cuya poblacion sea superior a la población del departamento con menor
 * IDH de toda la lista
 */
fun metodo10(deptos: IList<Departamento>): Double {
    //Comparar menor IDH------------ -----------------------------------------------------------------------------------
    val menor_idh = deptos.minWith(compareBy(Departamento::IDH))
    //filtrar lista por poblacion --------------------------------------------------------------------------------------
    val lista = deptos.filter { it.poblacion > menor_idh!!.poblacion }
    //lista superficie + sumar------ -----------------------------------------------------------------------------------
    val superficie_promedio = lista.map { it.superficie }.sum()
    //tamaño de la lista -----------------------------------------------------------------------------------------------
    val tamaño_lista = lista.size()
    return superficie_promedio.toDouble() / tamaño_lista
}
//-------------------------------------------------------------------
// Operaciones con la clase Municipio
//-------------------------------------------------------------------

/**
 * Determinar y retornar cuántos municipios de la lista son capitales
 */
fun metodo11(muns: IList<Municipio>): Int {
    //conteo de capitales ----------------------------------------------------------------------------------------------
    val capitales = muns.count { it.esCapital == true }
    return capitales
}

/*
 * Determinar el nombre del municipio que no es capital y que pertenece al
 * departamento que se recibe como parámetro y que tiene la población urbana
 * más grande
 */
fun metodo12(m: IList<Municipio>, depto: String): String {
    val municipio_no_capital = m.filter { it.esCapital == false }
    val pertenece_parametro = municipio_no_capital.filter { it.departamento == depto }
    val poblacion_mas_grande = pertenece_parametro.maxWith(compareBy(Municipio::poblacionUrbana))!!.nombre
    return poblacion_mas_grande
}

/**
 * Retornar el promedio de la población total (suma de la población rural y población urbana)
 * de aquellos municipios de la lista que pertenecen al departamento que se pasa
 * como parámetro y cuyo código sea múltiplo de 3 o de 5
 */
fun metodo13(municipios: IList<Municipio>, departamento: String): Double {

    val filtro_municipio = municipios.filter { it.departamento == departamento && (it.codigo%3 == 0 || it.codigo%5== 0) }

    val poblacion_total = (filtro_municipio.map { it.poblacionUrbana + it.poblacionRural }.sum().toDouble()/filtro_municipio.count())
    return poblacion_total
}

/**
 * Retorne el nombre del primer municipio que inicia con J en toda la lista
 */
fun metodo14(muns: IList<Municipio>): String {
    val letra ="J"
    val municipio_j = muns.filter { it.nombre.startsWith(letra) }.first.nombre
    return municipio_j
}


/**
 * Retorne cuantos municipios de la lista que tienen un código
 * de 4 dígitos poseen una poblacion rural superior a la población
 * urbana
 */
fun metodo15(muns: IList<Municipio>): Int {
    val filtro_codigo = muns.filter { it.poblacionRural > it.poblacionUrbana && it.codigo in 1000 .. 9999 }.count()
    return filtro_codigo
}

//-------------------------------------------------------------------
// Operaciones con la clase Producto
//-------------------------------------------------------------------

/*
 * Obtener el nombre de todos los productos cuyo código es par
 */
fun metodo1(productos: IList<Producto>): IList<String> {
    val productos_par = productos.filter { it.codigo %2 == 0 }
    val nombre_productos = productos_par.map { it.nombre }
    return nombre_productos
}

/**
 * Obtener cuántos productos tienen un precio inferior al producto
 * cuyo código se pasa como parámetro
 */
fun metodo2(productos: IList<Producto>, codProducto: Int): Int {
    val producto_parametro = productos.filter { it.codigo == codProducto }.map { it.precio }.first
    val precio_min = productos.filter { it.precio < producto_parametro }.count()
    return precio_min
}

/**
 * Obtener una lista con los códigos de los productos cuya cantidad sea
 * superior a la cantidad mínima que se pasa como parámetro y cuyo precio
 * esté entre mil y diez mil pesos.
 *
 */
fun metodo3(productos: IList<Producto>, cantidadMinima: Int): IList<Int> {
    val productos_superior_cant_min = productos.filter { it.cantidad > cantidadMinima && it.precio in 1000 .. 10000 }
    val codigos_obtenidos =  productos_superior_cant_min.map { it.codigo }
    return codigos_obtenidos
}

/**
 * EL inventario total de la lista es la suma de la multiplicación de la cantidad por el precio
 * de todos y cada uno de los productos de la lista. Este método permite saber si el
 * inventario de la lista es superior al millón de pesos o no.
 */
fun metodo4(prods: IList<Producto>): Boolean {
    val cantidad_productos = prods.map { it.cantidad }.sum()
    val precio_productos = prods.map { it.precio }.sum()
    val inventario = (cantidad_productos * precio_productos) > 1000000
    return  inventario
}

/**
 * Obtener el promedio de la cantidad de aquellos productos cuyo precio
 * esté por debajo del promedio de precio de todos los productos de la lista
 */
fun metodo5(prods: IList<Producto>): Double {

    val promedio_lista = prods.map { it.precio }.sum()/prods.count()
    val productos_menores = prods.filter { it.precio < promedio_lista }
    val nuevo_promedio = productos_menores.map{it.cantidad}.sum()/productos_menores.count().toDouble()
    return nuevo_promedio
}

//-------------------------------------------------------------------
// Operaciones con la clase Producto
//-------------------------------------------------------------------

fun metodo16(rects: IList<Rectangulo>): Int {
    val rectangulo_cuadrado = rects.filter { it.altura == it.base }.count()
    return rectangulo_cuadrado
}

fun metodo17(rects: IList<Rectangulo>): Double {

    val rectangulo_nuevo = rects.filter { it.base < it.altura }
    val area_rectangulo_nuevo = rectangulo_nuevo.map { it.area() }.sum()/rectangulo_nuevo.count()
    return area_rectangulo_nuevo
    //"Promedio del área de los rectangulos cuya base es inferior a su altura"
}

fun metodo18(rects: IList<Rectangulo>): Rectangulo {


    val rectangulo_mayor = rects.maxWith(compareBy(Rectangulo::area))
    return rectangulo_mayor!!
}

fun metodo19(rects: IList<Rectangulo>, areaMin: Double): IList<Double> {
    TODO("Lista con las diagonales de aquellos cuadrados cuya area sea superior al area que se pasa como parámetro")
}

/**
 * Un triangulo es rectangulo si un lado (el mas largo) es igual a la raiz cuadrada de
 * la suma de los cuadrados de los otros dos lados
 */
fun esRectangulo(t: Triangulo): Boolean {
    TODO("Retorna true si t es triangulo rectangulo")
}

fun areaTriangulo(t: Triangulo): Double {
    TODO("Hallar el área del triángulo a partir de la fórmula de Herón")
}

fun metodo20(triangulos: IList<Triangulo>): IList<Double> {
    TODO("Lista de áreas de aquellos triangulos rectangulos de la lista")
}

fun metodo21(triangulos: IList<Triangulo>): IList<Int> {
    fun esIsosceles(t: Triangulo) = t.lado1 == t.lado2 || t.lado2 == t.lado3 || t.lado1 == t.lado3
    //TODO("Obtener la lista de identificadores de aquellos triangulos isosceles cuya área no supere 10")
    return triangulos.filter { esIsosceles(it) && areaTriangulo(it) <= 10.0 }.map { it.id }
}