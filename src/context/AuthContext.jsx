import React, { createContext, useContext, useState, useEffect } from 'react'
import toast from 'react-hot-toast'

const AuthContext = createContext()

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth debe ser usado dentro de un AuthProvider')
  }
  return context
}

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // Simular carga del usuario desde localStorage
    const savedUser = localStorage.getItem('user')
    if (savedUser) {
      setUser(JSON.parse(savedUser))
    }
    setLoading(false)
  }, [])

  const login = async (email, password) => {
    try {
      setLoading(true)
      
      // Simulación de login - en producción sería una llamada a la API
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      // Usuario de prueba
      const userData = {
        id: '1',
        email,
        name: email === 'admin@farmacia.com' ? 'Administrador' : 'Usuario Demo',
        role: email === 'admin@farmacia.com' ? 'admin' : 'user',
        phone: '+57 300 123 4567',
        address: 'Calle 123 #45-67, Bogotá'
      }
      
      setUser(userData)
      localStorage.setItem('user', JSON.stringify(userData))
      toast.success('¡Bienvenido!')
      
      return { success: true }
    } catch (error) {
      toast.error('Error al iniciar sesión')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const register = async (userData) => {
    try {
      setLoading(true)
      
      // Simulación de registro
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      const newUser = {
        id: Date.now().toString(),
        ...userData,
        role: 'user'
      }
      
      setUser(newUser)
      localStorage.setItem('user', JSON.stringify(newUser))
      toast.success('¡Cuenta creada exitosamente!')
      
      return { success: true }
    } catch (error) {
      toast.error('Error al crear la cuenta')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const logout = () => {
    setUser(null)
    localStorage.removeItem('user')
    toast.success('Sesión cerrada')
  }

  const updateProfile = async (profileData) => {
    try {
      setLoading(true)
      
      // Simulación de actualización
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      const updatedUser = { ...user, ...profileData }
      setUser(updatedUser)
      localStorage.setItem('user', JSON.stringify(updatedUser))
      toast.success('Perfil actualizado')
      
      return { success: true }
    } catch (error) {
      toast.error('Error al actualizar el perfil')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const changePassword = async (currentPassword, newPassword) => {
    try {
      setLoading(true)
      
      // Simulación de cambio de contraseña
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      toast.success('Contraseña actualizada')
      return { success: true }
    } catch (error) {
      toast.error('Error al cambiar la contraseña')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const forgotPassword = async (email) => {
    try {
      setLoading(true)
      
      // Simulación de recuperación de contraseña
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      toast.success('Instrucciones enviadas a tu correo')
      return { success: true }
    } catch (error) {
      toast.error('Error al enviar las instrucciones')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const deleteAccount = async () => {
    try {
      setLoading(true)
      
      // Simulación de eliminación de cuenta
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      setUser(null)
      localStorage.removeItem('user')
      toast.success('Cuenta eliminada')
      
      return { success: true }
    } catch (error) {
      toast.error('Error al eliminar la cuenta')
      return { success: false, error: error.message }
    } finally {
      setLoading(false)
    }
  }

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    updateProfile,
    changePassword,
    forgotPassword,
    deleteAccount
  }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  )
}