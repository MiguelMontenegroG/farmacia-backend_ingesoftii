import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { Mail, ArrowLeft } from 'lucide-react'
import { useAuth } from '../../context/AuthContext'

const ForgotPassword = () => {
  const [email, setEmail] = useState('')
  const [errors, setErrors] = useState({})
  const [isSubmitted, setIsSubmitted] = useState(false)
  const { forgotPassword, loading } = useAuth()

  const handleChange = (e) => {
    setEmail(e.target.value)
    if (errors.email) {
      setErrors({})
    }
  }

  const validateForm = () => {
    const newErrors = {}

    if (!email) {
      newErrors.email = 'El correo electrónico es requerido'
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'El correo electrónico no es válido'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    if (!validateForm()) {
      return
    }

    const result = await forgotPassword(email)
    
    if (result.success) {
      setIsSubmitted(true)
    }
  }

  if (isSubmitted) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
        <div className="max-w-md w-full space-y-8">
          <div className="text-center">
            <div className="flex justify-center">
              <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center">
                <Mail className="h-8 w-8 text-green-600" />
              </div>
            </div>
            <h2 className="mt-6 text-3xl font-bold text-gray-900">
              ¡Correo Enviado!
            </h2>
            <p className="mt-2 text-sm text-gray-600">
              Hemos enviado las instrucciones para restablecer tu contraseña a:
            </p>
            <p className="mt-2 text-sm font-medium text-blue-600">
              {email}
            </p>
            <p className="mt-4 text-sm text-gray-600">
              Revisa tu bandeja de entrada y sigue las instrucciones del correo.
              Si no lo encuentras, revisa tu carpeta de spam.
            </p>
          </div>

          <div className="space-y-4">
            <Link
              to="/login"
              className="w-full btn btn-primary flex items-center justify-center gap-2"
            >
              <ArrowLeft className="h-4 w-4" />
              Volver al Inicio de Sesión
            </Link>
            
            <button
              onClick={() => setIsSubmitted(false)}
              className="w-full btn btn-outline"
            >
              Enviar a otro correo
            </button>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div>
          <div className="flex justify-center">
            <div className="w-16 h-16 bg-blue-600 rounded-full flex items-center justify-center">
              <span className="text-white font-bold text-2xl">F</span>
            </div>
          </div>
          <h2 className="mt-6 text-center text-3xl font-bold text-gray-900">
            Recuperar Contraseña
          </h2>
          <p className="mt-2 text-center text-sm text-gray-600">
            Ingresa tu correo electrónico y te enviaremos las instrucciones 
            para restablecer tu contraseña.
          </p>
        </div>

        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="email" className="form-label">
              Correo Electrónico
            </label>
            <div className="relative">
              <input
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                value={email}
                onChange={handleChange}
                className={`form-input pl-10 ${errors.email ? 'border-red-500' : ''}`}
                placeholder="tu@email.com"
              />
              <Mail className="absolute left-3 top-3 h-5 w-5 text-gray-400" />
            </div>
            {errors.email && (
              <p className="form-error">{errors.email}</p>
            )}
          </div>

          <div>
            <button
              type="submit"
              disabled={loading}
              className="w-full btn btn-primary"
            >
              {loading ? (
                <div className="flex items-center justify-center">
                  <div className="spinner mr-2"></div>
                  Enviando...
                </div>
              ) : (
                'Enviar Instrucciones'
              )}
            </button>
          </div>

          <div className="text-center">
            <Link
              to="/login"
              className="font-medium text-blue-600 hover:text-blue-500 flex items-center justify-center gap-2"
            >
              <ArrowLeft className="h-4 w-4" />
              Volver al Inicio de Sesión
            </Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default ForgotPassword