<?php

namespace charly\infrastructure\repository;

use PDO;
use charly\infrastructure\repository\interfaces\AuthRepositoryInterface;
use charly\core\dto\UserDTO;
use charly\core\domain\entity\User;

class AuthRepository implements AuthRepositoryInterface
{
    private PDO $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    /**
     * Enregistre un utilisateur
     * @param User $user
     */
    public function register(User $user): void
    {
        try {
            $stmt = $this->pdo->prepare('INSERT INTO users (id, nom, role, password) VALUES (:id, :nom, :role, :password)');
            $stmt->execute([
                'id' => $user->getID(),
                'nom' => $user->nom,
                'password' => password_hash($user->password, PASSWORD_DEFAULT),
                'role' => $user->role,
            ]);
        } catch (\Exception $e) {
            throw new PdoAuthException('Erreur lors de l\'enregistrement de l\'utilisateur : ' . $e->getMessage());
        }
    }

    /**
     * Authentifie un utilisateur par son nom
     * @param string $nom
     * @return UserDTO|null
     */
    public function login(string $nom): ?UserDTO
    {
        try {
            $stmt = $this->pdo->prepare('SELECT id, nom, password,role FROM users WHERE nom = :nom');
            $stmt->execute(['nom' => $nom]);
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            if ($row) {
                return new UserDTO($row['id'],$row['nom'], $row['password'], $row['role']);
            }
            return null;
        } catch (\Exception $e) {
            throw new PdoAuthException('Erreur lors de la connexion de l\'utilisateur : ' . $e->getMessage());
        }
    }

    /**
     * Récupère un utilisateur par son id
     * @param string $id
     * @return UserDTO|null
     */
    public function getUserById(string $id): ?UserDTO
    {
        try {
            $stmt = $this->pdo->prepare('SELECT * FROM users WHERE id = :id');
            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            if ($row) {
                return new UserDTO($row['id'], $row['nom'], $row['password'], $row['role']);
            }
            return null;
        } catch (\Exception $e) {
            throw new PdoAuthException('Erreur lors de la récupération de l\'utilisateur : ' . $e->getMessage());
        }
    }
}
