<?php

namespace charly\infrastructure\repository;

use charly\core\domain\entity\User;
use charly\core\dto\CompetenceWithInterestDTO;
use charly\core\dto\input\InputCreateSalarie;
use charly\core\dto\SalarieDTO;
use charly\core\dto\UserDTO;
use charly\core\domain\entity\Besoin;
use charly\core\dto\BesoinDTO;
use charly\core\dto\CompetenceDTO;
use charly\infrastructure\repository\interfaces\UserRepositoryInterface;
use PDO;
use Psr\Container\ContainerInterface;

class UserRepository implements UserRepositoryInterface
{
    protected PDO $pdo;

    public function __construct(ContainerInterface $container)
    {
        $this->pdo = $container->get('pdo');
    }
    public function getUserIdByNom(string $nom): ?string
    {
        $stmt = $this->pdo->prepare('SELECT id FROM users WHERE nom = :nom');
        $stmt->execute(['nom' => $nom]);
        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data ? $data['id'] : null;
    }

    public function getUserByNom(string $nom): ?User
    {
        $stmt = $this->pdo->prepare('SELECT * FROM users WHERE nom = :nom');
        $stmt->execute(['nom' => $nom]);
        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($data) {
            return new User($data['nom'], $data['id'], $data['role'], $data['password']);
        }
        return null;
    }

    public function createSalarie(InputCreateSalarie $input): User
    {
        $stmt = $this->pdo->prepare(
            "INSERT INTO users (nom, role) VALUES (:nom, :role) RETURNING id"
        );
        $stmt->execute([
            'nom'  => $input->getNom(),
            'role' => 1
        ]);
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $userId = $row['id'];

        $competences = $input->getCompetences();
        foreach ($competences as $comp) {
            $stmt = $this->pdo->prepare(
                "INSERT INTO user_competence (user_id, competence_id, interet) 
                 VALUES (:user_id, :competence_id, :interet)"
            );
            $stmt->execute([
                'user_id'       => $userId,
                'competence_id' => $comp['id'],
                'interet'       => $comp['note']
            ]);
        }

        return new User($input->getNom(), $userId, 1);
    }

    public function getSalaries(): array
    {
        $sql = "
            SELECT u.id AS user_id, u.nom AS user_nom, uc.interet, c.id AS competence_id, c.libelle AS competence_label
            FROM users u
            LEFT JOIN user_competence uc ON u.id = uc.user_id
            LEFT JOIN competence c ON uc.competence_id = c.id
            WHERE u.role = 1
            ORDER BY u.nom
        ";

        $stmt = $this->pdo->prepare($sql);
        $stmt->execute();
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

        $salaries = [];
        foreach ($rows as $row) {
            $userId = $row['user_id'];
            if (!isset($salaries[$userId])) {
                $salaries[$userId] = [
                    'id' => $userId,
                    'nom' => $row['user_nom'],
                    'competences' => []
                ];
            }
            if ($row['competence_id'] !== null) {
                $salaries[$userId]['competences'][] = new CompetenceWithInterestDTO(
                    (string)$row['competence_id'],
                    $row['competence_label'],
                    (int)$row['interet']
                );
            }
        }

        $result = [];
        foreach ($salaries as $sal) {
            $result[] = new SalarieDTO($sal['id'], $sal['nom'], 1, $sal['competences']);
        }

        return $result;
    }

    public function getUserBesoins(string $id): array
    {
        
        $sql = "
            SELECT b.id, b.libelle, b.competence_id, b.date, u.nom AS nom_client, c.libelle AS competence_label
            FROM besoin b
            LEFT JOIN users u ON b.client_id = u.id
            LEFT JOIN competence c ON b.competence_id = c.id
            WHERE b.client_id = :client_id
        ";
    
        $stmt = $this->pdo->prepare($sql);
        $stmt->bindParam(':client_id', $id, PDO::PARAM_STR);  
        $stmt->execute();
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
        $besoins = [];
        foreach ($rows as $row) {
            $besoinId = $row['id'];
    
            
            $nomClient = $row['nom_client'];
            $libelleBesoin = $row['libelle'];
            $idCompetence = $row['competence_id'];
            $timestamp = strtotime($row['date']);  
    
            
            if (!isset($besoins[$besoinId])) {
                $besoins[$besoinId] = new Besoin(
                    $nomClient,
                    $libelleBesoin,
                    $idCompetence,
                    $besoinId,
                    $timestamp
                );
            }
    
   
        }
    
        
        $result = [];
        foreach ($besoins as $besoin) {
            $result[] = new BesoinDTO($besoin);
        }
    
        return $result;
    }
    
        
    
}