import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { 
  ArrowLeft, 
  Star, 
  ShoppingCart, 
  Heart, 
  Share2, 
  Plus, 
  Minus,
  Shield,
  Truck,
  AlertTriangle
} from 'lucide-react'
import { useCart } from '../context/CartContext'
import toast from 'react-hot-toast'

const ProductDetail = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [product, setProduct] = useState(null)
  const [quantity, setQuantity] = useState(1)
  const [activeTab, setActiveTab] = useState('description')
  const [loading, setLoading] = useState(true)
  const { addToCart } = useCart()

  useEffect(() => {
    // Simulación de carga de producto
    const mockProduct = {
      id: id,
      name: 'Acetaminofén 500mg',
      description: 'Analgésico y antipirético para el alivio del dolor y la fiebre. Indicado para dolores de cabeza, dolores musculares, artritis, dolor de espalda, dolor de muelas, resfriados y fiebre.',
      price: 8500,
      images: [
        'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=600',
        'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=600',
        'https://images.pexels.com/photos/3683074/pexels-photo-3683074.jpeg?auto=compress&cs=tinysrgb&w=600'
      ],
      category: 'Analgésicos',
      rating: 4.5,
      reviewCount: 128,
      inStock: true,
      stock: 50,
      prescription: false,
      brand: 'Genfar',
      activeIngredient: 'Acetaminofén 500mg',
      presentation: 'Caja x 20 tabletas',
      indications: 'Dolor de cabeza, fiebre, dolores musculares, artritis menor, dolor de espalda, dolor de muelas, resfriados.',
      contraindications: 'Hipersensibilidad al acetaminofén. Insuficiencia hepática grave.',
      dosage: 'Adultos: 1-2 tabletas cada 4-6 horas. No exceder 8 tabletas en 24 horas.',
      sideEffects: 'Raros: náuseas, vómitos, reacciones alérgicas cutáneas.',
      storage: 'Conservar en lugar seco, a temperatura ambiente (15-30°C). Mantener fuera del alcance de los niños.',
      manufacturer: 'Genfar S.A.',
      registrationNumber: 'INVIMA 2019M-0012345',
      expiryDate: '2025-12-31'
    }

    setTimeout(() => {
      setProduct(mockProduct)
      setLoading(false)
    }, 1000)
  }, [id])

  const formatPrice = (price) => {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(price)
  }

  const handleAddToCart = () => {
    if (product.prescription) {
      toast.error('Este medicamento requiere receta médica')
      return
    }
    addToCart(product, quantity)
  }

  const handleQuantityChange = (change) => {
    const newQuantity = quantity + change
    if (newQuantity >= 1 && newQuantity <= product.stock) {
      setQuantity(newQuantity)
    }
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
      </div>
    )
  }

  if (!product) {
    return (
      <div className="container mx-auto px-4 py-8 text-center">
        <h2 className="text-2xl font-bold mb-4">Producto no encontrado</h2>
        <button
          onClick={() => navigate('/')}
          className="btn btn-primary"
        >
          Volver al inicio
        </button>
      </div>
    )
  }

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Breadcrumb */}
      <div className="flex items-center gap-2 mb-6 text-sm text-gray-600">
        <button
          onClick={() => navigate('/')}
          className="flex items-center gap-1 hover:text-blue-600 transition-colors"
        >
          <ArrowLeft className="h-4 w-4" />
          Volver
        </button>
        <span>/</span>
        <span>{product.category}</span>
        <span>/</span>
        <span className="text-gray-900">{product.name}</span>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
        {/* Imágenes del producto */}
        <div>
          <div className="mb-4">
            <img
              src={product.images[0]}
              alt={product.name}
              className="w-full h-96 object-cover rounded-lg shadow-lg"
            />
          </div>
          <div className="grid grid-cols-3 gap-2">
            {product.images.map((image, index) => (
              <img
                key={index}
                src={image}
                alt={`${product.name} ${index + 1}`}
                className="w-full h-24 object-cover rounded-lg cursor-pointer hover:opacity-75 transition-opacity"
              />
            ))}
          </div>
        </div>

        {/* Información del producto */}
        <div>
          <div className="mb-4">
            <h1 className="text-3xl font-bold mb-2">{product.name}</h1>
            <p className="text-gray-600 mb-2">{product.brand}</p>
            
            <div className="flex items-center gap-2 mb-4">
              <div className="flex items-center">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    className={`h-5 w-5 ${
                      i < Math.floor(product.rating)
                        ? 'text-yellow-400 fill-current'
                        : 'text-gray-300'
                    }`}
                  />
                ))}
              </div>
              <span className="text-sm text-gray-600">
                ({product.reviewCount} reseñas)
              </span>
            </div>

            {product.prescription && (
              <div className="bg-red-50 border border-red-200 rounded-lg p-3 mb-4">
                <div className="flex items-center gap-2 text-red-700">
                  <AlertTriangle className="h-5 w-5" />
                  <span className="font-medium">Requiere Receta Médica</span>
                </div>
                <p className="text-sm text-red-600 mt-1">
                  Este medicamento solo se puede vender con prescripción médica válida.
                </p>
              </div>
            )}
          </div>

          <div className="mb-6">
            <div className="text-3xl font-bold text-blue-600 mb-2">
              {formatPrice(product.price)}
            </div>
            <p className="text-gray-600">{product.presentation}</p>
          </div>

          {/* Controles de cantidad */}
          <div className="mb-6">
            <label className="form-label">Cantidad</label>
            <div className="flex items-center gap-3">
              <button
                onClick={() => handleQuantityChange(-1)}
                disabled={quantity <= 1}
                className="p-2 border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <Minus className="h-4 w-4" />
              </button>
              <span className="w-12 text-center font-medium">{quantity}</span>
              <button
                onClick={() => handleQuantityChange(1)}
                disabled={quantity >= product.stock}
                className="p-2 border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <Plus className="h-4 w-4" />
              </button>
              <span className="text-sm text-gray-600 ml-2">
                ({product.stock} disponibles)
              </span>
            </div>
          </div>

          {/* Botones de acción */}
          <div className="flex gap-3 mb-6">
            <button
              onClick={handleAddToCart}
              disabled={!product.inStock}
              className="flex-1 btn btn-primary flex items-center justify-center gap-2"
            >
              <ShoppingCart className="h-5 w-5" />
              {product.inStock ? 'Agregar al Carrito' : 'Agotado'}
            </button>
            <button className="btn btn-outline p-3">
              <Heart className="h-5 w-5" />
            </button>
            <button className="btn btn-outline p-3">
              <Share2 className="h-5 w-5" />
            </button>
          </div>

          {/* Información de envío */}
          <div className="space-y-3 text-sm">
            <div className="flex items-center gap-2 text-green-600">
              <Truck className="h-4 w-4" />
              <span>Envío gratis en compras superiores a $50.000</span>
            </div>
            <div className="flex items-center gap-2 text-blue-600">
              <Shield className="h-4 w-4" />
              <span>Producto 100% original y garantizado</span>
            </div>
          </div>
        </div>
      </div>

      {/* Tabs de información */}
      <div className="bg-white rounded-lg shadow-lg">
        <div className="border-b">
          <nav className="flex space-x-8 px-6">
            {[
              { id: 'description', label: 'Descripción' },
              { id: 'indications', label: 'Indicaciones' },
              { id: 'dosage', label: 'Dosificación' },
              { id: 'warnings', label: 'Advertencias' }
            ].map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`py-4 px-2 border-b-2 font-medium text-sm transition-colors ${
                  activeTab === tab.id
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700'
                }`}
              >
                {tab.label}
              </button>
            ))}
          </nav>
        </div>

        <div className="p-6">
          {activeTab === 'description' && (
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Descripción del Producto</h3>
              <p className="text-gray-700">{product.description}</p>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-6">
                <div>
                  <h4 className="font-medium mb-2">Principio Activo</h4>
                  <p className="text-gray-600">{product.activeIngredient}</p>
                </div>
                <div>
                  <h4 className="font-medium mb-2">Presentación</h4>
                  <p className="text-gray-600">{product.presentation}</p>
                </div>
                <div>
                  <h4 className="font-medium mb-2">Fabricante</h4>
                  <p className="text-gray-600">{product.manufacturer}</p>
                </div>
                <div>
                  <h4 className="font-medium mb-2">Registro INVIMA</h4>
                  <p className="text-gray-600">{product.registrationNumber}</p>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'indications' && (
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Indicaciones</h3>
              <p className="text-gray-700">{product.indications}</p>
            </div>
          )}

          {activeTab === 'dosage' && (
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Dosificación</h3>
              <p className="text-gray-700">{product.dosage}</p>
              <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
                <h4 className="font-medium text-blue-800 mb-2">Importante</h4>
                <p className="text-blue-700 text-sm">
                  Siempre consulte con un profesional de la salud antes de usar este medicamento. 
                  No exceda la dosis recomendada.
                </p>
              </div>
            </div>
          )}

          {activeTab === 'warnings' && (
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Advertencias y Precauciones</h3>
              
              <div>
                <h4 className="font-medium mb-2 text-red-600">Contraindicaciones</h4>
                <p className="text-gray-700">{product.contraindications}</p>
              </div>

              <div>
                <h4 className="font-medium mb-2 text-orange-600">Efectos Secundarios</h4>
                <p className="text-gray-700">{product.sideEffects}</p>
              </div>

              <div>
                <h4 className="font-medium mb-2">Almacenamiento</h4>
                <p className="text-gray-700">{product.storage}</p>
              </div>

              <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
                <h4 className="font-medium text-yellow-800 mb-2">⚠️ Aviso Legal</h4>
                <p className="text-yellow-700 text-sm">
                  La información aquí contenida no sustituye el consejo médico profesional. 
                  Consulte siempre con su médico o farmacéutico antes de usar este medicamento.
                </p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default ProductDetail