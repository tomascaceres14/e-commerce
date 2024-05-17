import React from 'react'

export const Login = () => {
  return (
    <div>
        <form>
            <label>Username</label>
            <input type='email' />
            <label>Password</label>
            <input type='password' />
            <button className=''>Send</button>
        </form>
    </div>
  )
}
