<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <header class="bg-[#375a69] shadow-md text-white  px-6 py-6 mb-8 font-semibold">

      <nav class="mx-auto flex space-x-4 items-center justify-between">
        <div>
          <RouterLink to="/" class="text-2xl hover:text-[#b2bf00]">PerfectMatch</RouterLink>
        </div>
        <div class="space-x-4">
          <RouterLink to="/besoins/add" class="hover:text-[#b2bf00]">Ajouter un besoin</RouterLink>
          <RouterLink to="/login" class="hover:text-[#b2bf00]">Se connecter</RouterLink>
          <RouterLink to="/profil" class="hover:text-[#b2bf00]">Profil</RouterLink>
        </div>

      </nav>
    </header>

    <main class="mx-auto px-4 py-8">
      <RouterView />
    </main>
  </div>
</template>
