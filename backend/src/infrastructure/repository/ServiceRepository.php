<?php
namespace charly\infrastructure\repository;

use charly\infrastructure\repository\interfaces\ServiceRepositoryInterface;
use PDO;
use charly\core\dto\ServiceDTO;

class ServiceRepository implements ServiceRepositoryInterface
{
    private PDO $pdo;

    public function __construct(PDO $pdo)
    {
        $this->pdo = $pdo;
    }

    public function getServicesByUserId(string $userid): array
    {
        $query = "
            SELECT s.id, s.client_id, s.salarie_id, s.id_besoin
            FROM service s
            JOIN besoin b ON s.id_besoin = b.id
            JOIN competence c ON b.competence_id = c.id
            WHERE s.client_id = :userid OR s.salarie_id = :userid
        ";

        $stmt = $this->pdo->prepare($query);
        $stmt->bindParam(':userid', $userid, PDO::PARAM_STR);
        $stmt->execute();

        $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $serviceDTOs = [];

        foreach ($results as $row) {
            $serviceDTOs[] = new ServiceDTO(
                (int)$row['id'],
                (string)$row['client_id'],
                (string)$row['salarie_id'],
                (int)$row['id_besoin']
            );
        }

        return $serviceDTOs;
    }
}
