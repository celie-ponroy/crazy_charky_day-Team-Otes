<?php

namespace charly\core\service;

use charly\core\dto\UserDTO;
use charly\core\domain\entity\User;
use charly\providers\auth\JWTManager;
use charly\core\service\interfaces\UserAuthServiceInterface;
use charly\infrastructure\repository\interfaces\AuthRepositoryInterface;
use charly\infrastructure\repository\PdoAuthException;

class UserAuthService implements UserAuthServiceInterface
{
    protected AuthRepositoryInterface $authRepository;
    private JWTManager $jwtManager;

    public function __construct(AuthRepositoryInterface $authRepository , JWTManager $jwtManager)
    {
        $this->authRepository = $authRepository;
        $this->jwtManager = $jwtManager;
    }

    /**
     * Méthode pour enregistrer un utilisateur
     */
    public function register(string $nom, string $password): void
    {
        try {
            $user = new User($nom, $password);
            $this->authRepository->register($user);
        } catch (PdoAuthException $e) {
            throw new AuthenticationException($e->getMessage());
        }
    }

    /**
     * Méthode pour authentifier un utilisateur
     */
    public function login(string $nom, string $password): UserDTO
    {
        try {
            $userDTO = $this->authRepository->login($nom);

            if (!$userDTO || !password_verify($password, $userDTO->hashed_password)) {
                throw new AuthenticationException('Identifiants invalides');
            }

            $accessToken = $this->jwtManager->createAccessToken([
                'id' => $userDTO->id,
                'role' => $userDTO->role,
                'exp' => time() + 3600, // Access token valable 1 heure
            ]);

            $refreshToken = $this->jwtManager->createRefreshToken([
                'id' => $userDTO->id,
                'role' => $userDTO->role,
                'exp' => time() + 86400, // Refresh token valable 24 heures
            ]);

            $userDTO->setAccessToken($accessToken);
            $userDTO->setRefreshToken($refreshToken);
            return $userDTO;
        } catch (PdoAuthException | \Exception $e) {
            throw new AuthenticationException($e->getMessage());
        }
    }

    /**
     * Méthode pour recuperer un utilisateur par son id
     */
    public function getUserById(string $id): UserDTO
    {
        try {
            return $this->authRepository->getUserById($id);
        } catch (PdoAuthException $e) {
            throw new AuthenticationException($e->getMessage());
        }
    }
}