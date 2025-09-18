import React, { useState, useEffect } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { 
  Search, 
  Filter, 
  Star, 
  ShoppingCart, 
  Eye,
  Heart,
  Truck,
  Shield,
  Clock,
  Award
} from 'lucide-react'
import { useCart } from '../context/CartContext'
import toast from 'react-hot-toast'

const Home = () => {
  const [searchParams] = useSearchParams()
  const [products, setProducts] = useState([])
  const [categories, setCategories] = useState([])
  const [filteredProducts, setFilteredProducts] = useState([])
  const [searchQuery, setSearchQuery] = useState(searchParams.get('search') || '')
  const [selectedCategory, setSelectedCategory] = useState('')
  const [priceRange, setPriceRange] = useState({ min: '', max: '' })
  const [sortBy, setSortBy] = useState('name')
  const [loading, setLoading] = useState(true)
  const { addToCart } = useCart()

  // Datos de ejemplo
  useEffect(() => {
    const mockProducts = [
      {
        id: '1',
        name: 'Acetaminofén 500mg',
        description: 'Analgésico y antipirético para el alivio del dolor y la fiebre',
        price: 8500,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Analgésicos',
        rating: 4.5,
        inStock: true,
        prescription: false,
        brand: 'Genfar'
      },
      {
        id: '2',
        name: 'Ibuprofeno 400mg',
        description: 'Antiinflamatorio no esteroideo para dolor e inflamación',
        price: 12000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Antiinflamatorios',
        rating: 4.3,
        inStock: true,
        prescription: false,
        brand: 'MK'
      },
      {
        id: '3',
        name: 'Vitamina C 1000mg',
        description: 'Suplemento vitamínico para fortalecer el sistema inmunológico',
        price: 25000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Vitaminas',
        rating: 4.7,
        inStock: true,
        prescription: false,
        brand: 'Nature\'s Bounty'
      },
      {
        id: '4',
        name: 'Omeprazol 20mg',
        description: 'Inhibidor de la bomba de protones para problemas gástricos',
        price: 18000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Gastroenterología',
        rating: 4.4,
        inStock: true,
        prescription: true,
        brand: 'Tecnoquímicas'
      },
      {
        id: '5',
        name: 'Loratadina 10mg',
        description: 'Antihistamínico para alergias y rinitis',
        price: 15000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Alergias',
        rating: 4.2,
        inStock: true,
        prescription: false,
        brand: 'Lafrancol'
      },
      {
        id: '6',
        name: 'Crema Hidratante Facial',
        description: 'Crema hidratante para todo tipo de piel',
        price: 35000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Cuidado Personal',
        rating: 4.6,
        inStock: true,
        prescription: false,
        brand: 'Eucerin'
      },
      {
        id: '7',
        name: 'Termómetro Digital',
        description: 'Termómetro digital de lectura rápida',
        price: 45000,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Dispositivos Médicos',
        rating: 4.8,
        inStock: true,
        prescription: false,
        brand: 'Omron'
      },
      {
        id: '8',
        name: 'Alcohol Antiséptico 70%',
        description: 'Alcohol antiséptico para desinfección',
        price: 6500,
        image: 'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=400',
        category: 'Antisépticos',
        rating: 4.1,
        inStock: true,
        prescription: false,
        brand: 'JGB'
      }
    ]

    const mockCategories = [
      'Analgésicos',
      'Antiinflamatorios',
      'Vitaminas',
      'Gastroenterología',
      'Alergias',
      'Cuidado Personal',
      'Dispositivos Médicos',
      'Antisépticos'
    ]

    setProducts(mockProducts)
    setCategories(mockCategories)
    setFilteredProducts(mockProducts)
    setLoading(false)
  }, [])

  // Filtrar productos
  useEffect(() => {
    let filtered = [...products]

    // Filtro por búsqueda
    if (searchQuery) {
      filtered = filtered.filter(product =>
        product.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        product.description.toLowerCase().includes(searchQuery.toLowerCase()) ||
        product.category.toLowerCase().includes(searchQuery.toLowerCase())
      )
    }

    // Filtro por categoría
    if (selectedCategory) {
      filtered = filtered.filter(product => product.category === selectedCategory)
    }

    // Filtro por precio
    if (priceRange.min) {
      filtered = filtered.filter(product => product.price >= parseInt(priceRange.min))
    }
    if (priceRange.max) {
      filtered = filtered.filter(product => product.price <= parseInt(priceRange.max))
    }

    // Ordenar
    filtered.sort((a, b) => {
      switch (sortBy) {
        case 'price-low':
          return a.price - b.price
        case 'price-high':
          return b.price - a.price
        case 'rating':
          return b.rating - a.rating
        case 'name':
        default:
          return a.name.localeCompare(b.name)
      }
    })

    setFilteredProducts(filtered)
  }, [products, searchQuery, selectedCategory, priceRange, sortBy])

  const formatPrice = (price) => {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(price)
  }

  const handleAddToCart = (product) => {
    if (product.prescription) {
      toast.error('Este medicamento requiere receta médica')
      return
    }
    addToCart(product)
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
      </div>
    )
  }

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="hero-section py-16">
        <div className="container mx-auto px-4 text-center">
          <h1 className="text-4xl md:text-6xl font-bold mb-6">
            Tu Farmacia de Confianza
          </h1>
          <p className="text-xl mb-8 max-w-2xl mx-auto">
            Encuentra todos los medicamentos y productos de salud que necesitas 
            con la garantía de calidad que mereces
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link to="#productos" className="btn btn-primary text-lg px-8 py-3">
              Ver Productos
            </Link>
            <Link to="/categories" className="btn btn-outline text-lg px-8 py-3">
              Explorar Categorías
            </Link>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            <div className="text-center">
              <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Truck className="h-8 w-8 text-blue-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Entrega Rápida</h3>
              <p className="text-gray-600">Entrega en 24-48 horas en toda la ciudad</p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Shield className="h-8 w-8 text-green-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Productos Originales</h3>
              <p className="text-gray-600">100% garantía de autenticidad</p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Clock className="h-8 w-8 text-purple-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Atención 24/7</h3>
              <p className="text-gray-600">Servicio al cliente disponible siempre</p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-yellow-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Award className="h-8 w-8 text-yellow-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Mejor Precio</h3>
              <p className="text-gray-600">Precios competitivos garantizados</p>
            </div>
          </div>
        </div>
      </section>

      {/* Aviso Legal */}
      <section className="py-8 bg-yellow-50">
        <div className="container mx-auto px-4">
          <div className="legal-notice">
            <h3>⚠️ Aviso Importante sobre Medicamentos</h3>
            <p>
              Los medicamentos deben ser utilizados bajo supervisión médica. 
              No se automedique. Consulte siempre con un profesional de la salud 
              antes de usar cualquier medicamento. La venta de medicamentos con 
              receta está sujeta a la presentación de prescripción médica válida.
            </p>
          </div>
        </div>
      </section>

      {/* Productos */}
      <section id="productos" className="py-16">
        <div className="container mx-auto px-4">
          <div className="flex flex-col lg:flex-row gap-8">
            {/* Filtros */}
            <div className="lg:w-1/4">
              <div className="filter-sidebar">
                <h3 className="text-lg font-semibold mb-4 flex items-center gap-2">
                  <Filter className="h-5 w-5" />
                  Filtros
                </h3>

                {/* Búsqueda */}
                <div className="mb-6">
                  <label className="form-label">Buscar</label>
                  <div className="relative">
                    <input
                      type="text"
                      placeholder="Buscar productos..."
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                      className="form-input pl-10"
                    />
                    <Search className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                  </div>
                </div>

                {/* Categorías */}
                <div className="category-filter">
                  <label className="form-label">Categoría</label>
                  <select
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                    className="form-input"
                  >
                    <option value="">Todas las categorías</option>
                    {categories.map(category => (
                      <option key={category} value={category}>
                        {category}
                      </option>
                    ))}
                  </select>
                </div>

                {/* Rango de precios */}
                <div className="mb-6">
                  <label className="form-label">Rango de Precio</label>
                  <div className="price-range">
                    <input
                      type="number"
                      placeholder="Min"
                      value={priceRange.min}
                      onChange={(e) => setPriceRange(prev => ({ ...prev, min: e.target.value }))}
                      className="price-input"
                    />
                    <span>-</span>
                    <input
                      type="number"
                      placeholder="Max"
                      value={priceRange.max}
                      onChange={(e) => setPriceRange(prev => ({ ...prev, max: e.target.value }))}
                      className="price-input"
                    />
                  </div>
                </div>

                {/* Ordenar */}
                <div className="mb-6">
                  <label className="form-label">Ordenar por</label>
                  <select
                    value={sortBy}
                    onChange={(e) => setSortBy(e.target.value)}
                    className="form-input"
                  >
                    <option value="name">Nombre A-Z</option>
                    <option value="price-low">Precio: Menor a Mayor</option>
                    <option value="price-high">Precio: Mayor a Menor</option>
                    <option value="rating">Mejor Calificación</option>
                  </select>
                </div>
              </div>
            </div>

            {/* Lista de productos */}
            <div className="lg:w-3/4">
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-bold">
                  Productos ({filteredProducts.length})
                </h2>
              </div>

              {filteredProducts.length === 0 ? (
                <div className="text-center py-12">
                  <Search className="h-16 w-16 text-gray-300 mx-auto mb-4" />
                  <h3 className="text-lg font-medium text-gray-500 mb-2">
                    No se encontraron productos
                  </h3>
                  <p className="text-gray-400">
                    Intenta ajustar los filtros de búsqueda
                  </p>
                </div>
              ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                  {filteredProducts.map(product => (
                    <div key={product.id} className="product-card card">
                      <div className="relative">
                        <img
                          src={product.image}
                          alt={product.name}
                          className="w-full h-48 object-cover"
                        />
                        {product.prescription && (
                          <div className="absolute top-2 left-2 bg-red-500 text-white text-xs px-2 py-1 rounded">
                            Receta Médica
                          </div>
                        )}
                        <button className="absolute top-2 right-2 p-2 bg-white rounded-full shadow-md hover:bg-gray-50 transition-colors">
                          <Heart className="h-4 w-4 text-gray-600" />
                        </button>
                      </div>
                      
                      <div className="p-4">
                        <div className="flex items-center gap-2 mb-2">
                          <div className="flex items-center">
                            {[...Array(5)].map((_, i) => (
                              <Star
                                key={i}
                                className={`h-4 w-4 ${
                                  i < Math.floor(product.rating)
                                    ? 'text-yellow-400 fill-current'
                                    : 'text-gray-300'
                                }`}
                              />
                            ))}
                          </div>
                          <span className="text-sm text-gray-600">
                            ({product.rating})
                          </span>
                        </div>
                        
                        <h3 className="font-semibold mb-2 truncate">
                          {product.name}
                        </h3>
                        
                        <p className="text-gray-600 text-sm mb-3 line-clamp-2">
                          {product.description}
                        </p>
                        
                        <div className="flex items-center justify-between mb-3">
                          <span className="text-lg font-bold text-blue-600">
                            {formatPrice(product.price)}
                          </span>
                          <span className="text-sm text-gray-500">
                            {product.brand}
                          </span>
                        </div>
                        
                        <div className="flex gap-2">
                          <Link
                            to={`/product/${product.id}`}
                            className="flex-1 btn btn-outline btn-sm"
                          >
                            <Eye className="h-4 w-4" />
                            Ver
                          </Link>
                          <button
                            onClick={() => handleAddToCart(product)}
                            className="flex-1 btn btn-primary btn-sm"
                            disabled={!product.inStock}
                          >
                            <ShoppingCart className="h-4 w-4" />
                            {product.inStock ? 'Agregar' : 'Agotado'}
                          </button>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}

export default Home