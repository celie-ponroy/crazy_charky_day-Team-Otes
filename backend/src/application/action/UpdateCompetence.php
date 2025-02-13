<?php
namespace charly\application\action;

use charly\application\action\AbstractAction;
use charly\core\service\interfaces\CompetenceServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class UpdateCompetence extends AbstractAction
{
    private CompetenceServiceInterface $competenceService;

    public function __construct(CompetenceServiceInterface $competenceService)
    {
        $this->competenceService = $competenceService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $id = $args['id'];
            $data = (array)$rq->getParsedBody();
            $libelle = $data['libelle'];
            $description = $data['description'];

            $this->competenceService->updateCompetence($id, $libelle, $description);

            $responseData = [
                'success' => true,
                'message' => 'Compétence mise à jour avec succès'
            ];

            $rs->getBody()->write(json_encode($responseData));
            return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);
        } catch (\Exception $e) {
            return $this->respondWithError($rs, $e->getMessage(), 500);
        }
    }

    private function respondWithError(ResponseInterface $response, string $message, int $status): ResponseInterface
    {
        $responseData = [
            'status' => $status,
            'error' => $message
        ];

        $response->getBody()->write(json_encode($responseData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

        return $response->withHeader('Content-Type', 'application/json')->withStatus($status);
    }
}
